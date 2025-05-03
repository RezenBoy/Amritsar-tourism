package com.mgcfgs.amritsartourism.amritsar_tourism.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.Booking;
import com.mgcfgs.amritsartourism.amritsar_tourism.model.Hotel;
import com.mgcfgs.amritsartourism.amritsar_tourism.model.RegisterUser;
import com.mgcfgs.amritsartourism.amritsar_tourism.model.Room;
import com.mgcfgs.amritsartourism.amritsar_tourism.service.BookingService;
import com.mgcfgs.amritsartourism.amritsar_tourism.service.HotelService;
import com.mgcfgs.amritsartourism.amritsar_tourism.service.RoomService;
import com.mgcfgs.amritsartourism.amritsar_tourism.service.UserServices;

@Controller
@RequestMapping("/admin") // Base path for all methods in this controller
public class AdminController {

    private final HotelService hotelService;

    @Autowired
    UserServices registerUserService; // Assuming this service is used for user management

    @Autowired
    RoomService roomService; // Assuming this service is used for room management

    @Autowired
    BookingService bookingService;

    AdminController(HotelService hotelService) {
        this.hotelService = hotelService;
    } // Assuming this service is used for booking management

    @GetMapping("/dashboard") // Will map to /admin/dashboard
    public String showAdminPage() {
        return "admin/dashboard"; // Points to templates/admin/dashboard.html
    }

    // Additional admin endpoints can be added here
    @GetMapping("/users")
    public String showUsersPage(Model model) {
        List<RegisterUser> users = registerUserService.getAllUsers();
        model.addAttribute("users", users); // Add users to the model for display
        return "admin/users"; // Would point to templates/admin/users.html
    }

    @GetMapping("/bookings")
    public String showHotelsPage(Model model) {
        // Assuming you have a BookingService to fetch bookings
        List<Booking> bookings = bookingService.findAllBookings();
        model.addAttribute("bookings", bookings); // Add bookings to the model for display
        return "admin/bookings"; // Would point to templates/admin/hotels.html
    }

    @GetMapping("/addroom")
    public String showAddRoomForm(Model model) {
        // Assuming you have a Room class and a RoomService to handle room operations
        List<Hotel> hotels = hotelService.getAllHotels(); // Fetch all hotels
        model.addAttribute("room", new Room());
        model.addAttribute("hotels", hotels); // Add hotels to the model for display
        // Assuming you have a HotelService to fetch hotels
        return "admin/addroom";
    }

    @PostMapping("/addroom")
    public String addRoom(@Valid @ModelAttribute("room") Room room, BindingResult bindingResult,
            RedirectAttributes redirectAttributes, Model model) {
        // if (bindingResult.hasErrors()) {
        //     List<Hotel> hotels = hotelService.getAllHotels();
        //     model.addAttribute("hotels", hotels);
        //     return "admin/addroom";
        // }

        try {
            Long hotelId = room.getHotel().getId();
            Hotel hotel = hotelService.getHotelById(hotelId);
            if (hotel == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Selected hotel does not exist.");
                return "redirect:/admin/addroom";
            }

            // Set the managed Hotel entity on the Room
            room.setHotel(hotel);
            roomService.saveRoom(room);
            redirectAttributes.addFlashAttribute("successMessage", "Room added successfully!");
            return "redirect:/admin/rooms";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add room: " + e.getMessage());
            return "redirect:/admin/addroom";
        }
    }

    @GetMapping("/rooms")
    public String viewRooms(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "admin/managerooms";
    }

    @GetMapping("/add-hotel")
    public String showAddHotelForm(Model model) {
        model.addAttribute("hotel", new Hotel()); // Assuming you have a Hotel class
        return "admin/addhotel";
    }

    @PostMapping("/add-hotel")
    public String addHotel(
            @Valid @ModelAttribute("hotel") Hotel hotel,
            BindingResult result,
            @RequestParam("imageFile") MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/add-hotel";
        }

        // Handle file upload
        if (!imageFile.isEmpty()) {
            try {
                // Generate a unique file name to avoid conflicts
                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                // Define the upload directory
                Path uploadPath = Paths.get("src/main/resources/static/img/hotels/");
                // Create the directory if it doesn't exist
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                // Save the file to the server
                Path filePath = uploadPath.resolve(fileName);
                Files.write(filePath, imageFile.getBytes());
                // Set the image path in the Hotel entity
                hotel.setImagePath("/img/hotels/" + fileName);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Failed to upload image: " + e.getMessage());
                return "admin/add-hotel";
            }
        }
        if (!imageFile.isEmpty()) {
            // Validate file type
            String contentType = imageFile.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Please upload a valid image file (JPEG, PNG, etc.)");
                return "admin/add-hotel";
            }
            // Validate file size (e.g., max 5MB)
            if (imageFile.getSize() > 5 * 1024 * 1024) {
                redirectAttributes.addFlashAttribute("errorMessage", "Image file size must be less than 5MB");
                return "admin/add-hotel";
            }
            // Proceed with file saving...
        }
        hotelService.addHotel(hotel);
        redirectAttributes.addFlashAttribute("successMessage", "Hotel added successfully!");
        return "redirect:/admin/manage-hotel";
    }

    @GetMapping("/manage-hotel")
    public String manageHotel(Model model) {
        List<Hotel> hotels = hotelService.getAllHotels(); // Fetch all hotels
        model.addAttribute("hotels", hotels); // Add hotels to the model for display
        // Assuming you have a HotelService to fetch hotels
        // List<Room> rooms = roomService.getAllRooms();
        // model.addAttribute("rooms", rooms); // Add hotels to the model for display
        return "admin/managehotel"; // Would point to templates/admin/managehotel.html
    }

}
