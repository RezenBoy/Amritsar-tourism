package com.mgcfgs.amritsartourism.amritsar_tourism.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.nio.file.Path;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/admin") // Base path for all methods in this controller
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final HotelService hotelService;

    private final UserServices userService; // Assuming this service is used for user management
    private final RoomService roomService; // Assuming this service is used for room management
    private final BookingService bookingService; // Assuming this service is used for booking management

    public AdminController(UserServices userService, RoomService roomService, BookingService bookingService,
            HotelService hotelService) {
        this.userService = userService;
        this.roomService = roomService;
        this.bookingService = bookingService;
        this.hotelService = hotelService;
    }
    // Constructor injection for the HotelService

    @GetMapping("/dashboard") // Will map to /admin/dashboard
    public String showAdminPage() {
        return "admin/dashboard"; // Points to templates/admin/dashboard.html
    }

    // Additional admin endpoints can be added here
    @GetMapping("/users")
    public String showUsersPage(Model model) {
        List<RegisterUser> users = userService.getAllUsers();
        model.addAttribute("users", users); // Add users to the model for display
        return "admin/users"; // Would point to templates/admin/users.html
    }

    @PostMapping("/users/delete")
    public String deleteUser(
            @RequestParam("id") Long id,
            RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUserById(id);
            redirectAttributes.addFlashAttribute("message", "User deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting user: " + e.getMessage());
        }
        return "redirect:/admin/users";
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
        // List<Hotel> hotels = hotelService.getAllHotels();
        // model.addAttribute("hotels", hotels);
        // return "admin/addroom";
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

    @PostMapping("/delete-hotel")
    public String deleteHotel(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            hotelService.deleteHotel(id);
            redirectAttributes.addFlashAttribute("successMessage", "Hotel deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete hotel: " + e.getMessage());
        }
        return "redirect:/admin/manage-hotel";
    }

    @PostMapping("/delete-room")
    public String deleteRoom(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            roomService.deleteRoom(id);
            redirectAttributes.addFlashAttribute("successMessage", "Room deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete room: " + e.getMessage());
        }
        return "redirect:/admin/rooms";
    }

    @PostMapping("/bookings/delete/{id}")
    public String deleteBooking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        logger.info("Deleting booking ID {}", id);
        try {
            bookingService.deleteBooking(id);
            redirectAttributes.addFlashAttribute("successMessage", "Booking deleted successfully!");
        } catch (Exception e) {
            logger.error("Failed to delete booking with ID {}: {}", id, e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete booking: " + e.getMessage());
        }
        return "redirect:/admin/bookings";
    }

    @GetMapping("/hotels/{id}/edit") // Now this will be /admin/hotels/{id}/edit
    public String showEditHotelForm(@PathVariable("id") Long id,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            Hotel hotel = hotelService.getHotelById(id);
            // .orElseThrow(() -> new EntityNotFoundException("Hotel not found"));
            model.addAttribute("hotel", hotel);
            return "admin/edit-hotel"; // Make sure this template exists
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/hotels";
        }
    }
}
