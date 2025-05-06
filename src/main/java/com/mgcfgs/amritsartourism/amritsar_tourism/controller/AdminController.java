package com.mgcfgs.amritsartourism.amritsar_tourism.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
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

    // @GetMapping("/delete-booking")
    // public String deleteBooking(@RequestParam("id") Long id, RedirectAttributes
    // redirectAttributes) {
    // try {
    // bookingService.deleteBooking(id);
    // redirectAttributes.addFlashAttribute("successMessage", "Booking deleted
    // successfully!");
    // } catch (Exception e) {
    // redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete
    // booking: " + e.getMessage());
    // }
    // return "redirect:/admin/bookings";
    // }

    // @GetMapping("/bookings")
    // public String showBookingsPage(
    //         @RequestParam(value = "page", defaultValue = "1") int page,
    //         @RequestParam(value = "size", defaultValue = "10") int size,
    //         @RequestParam(value = "status", required = false) String status,
    //         Model model) {
    //     logger.info("Accessing bookings page: page={}, size={}, status={}", page, size, status);
    //     Pageable pageable = PageRequest.of(page - 1, size);
    //     Page<Booking> bookingPage = bookingService.getBookings(pageable, status);
    //     model.addAttribute("bookings", bookingPage.getContent());
    //     model.addAttribute("currentPage", page);
    //     model.addAttribute("totalPages", bookingPage.getTotalPages());
    //     model.addAttribute("size", size);
    //     model.addAttribute("currentStatus", status != null ? status : "ALL");
    //     List<RegisterUser> users = userService.findAll();
    //     List<Room> rooms = roomService.findAll();
    //     model.addAttribute("users", users);
    //     model.addAttribute("rooms", rooms);
    //     model.addAttribute("booking", new Booking()); // Ensure a new Booking object is always added
    //     return "admin/managebookings";
    // }

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

    @GetMapping("/delete-user")
    public String deleteUser(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUserById(id);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete user: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    // @GetMapping("/edit-user")
    // public String showEditUserForm(@RequestParam("id") Long id, Model model) {
    // RegisterUser user = userService.getUserById(id);
    // if (user != null) {
    // model.addAttribute("user", user); // Add the user to the model for editing
    // return "admin/edit-user"; // Points to templates/admin/edit-user.html
    // } else {
    // return "redirect:/admin/users"; // Redirect if user not found
    // }
    // }

    // @PostMapping("/edit-user")
    // public String editUser(@Valid @ModelAttribute("user") RegisterUser user,
    // BindingResult bindingResult,
    // RedirectAttributes redirectAttributes) {
    // if (bindingResult.hasErrors()) {
    // return "admin/edit-user"; // Return to the edit form if there are validation
    // errors
    // }
    // try {
    // userService.updateUser(user); // Assuming you have a method to update the
    // user
    // redirectAttributes.addFlashAttribute("successMessage", "User updated
    // successfully!");
    // } catch (Exception e) {
    // redirectAttributes.addFlashAttribute("errorMessage", "Failed to update user:
    // " + e.getMessage());
    // }
    // return "redirect:/admin/users"; // Redirect to the users page after editing
    // }

    // @GetMapping("/add-user")
    // public String showAddUserForm(Model model) {
    // model.addAttribute("user", new RegisterUser()); // Assuming you have a
    // RegisterUser class
    // return "admin/add-user"; // Points to templates/admin/add-user.html
    // }

    // @PostMapping("/add-user")
    // public String addUser(@Valid @ModelAttribute("user") RegisterUser user,
    // BindingResult bindingResult,
    // RedirectAttributes redirectAttributes) {
    // if (bindingResult.hasErrors()) {
    // return "admin/add-user"; // Return to the add form if there are validation
    // errors
    // }
    // try {
    // userService.addUser(user); // Assuming you have a method to add the user
    // redirectAttributes.addFlashAttribute("successMessage", "User added
    // successfully!");
    // } catch (Exception e) {
    // redirectAttributes.addFlashAttribute("errorMessage", "Failed to add user: " +
    // e.getMessage());
    // }
    // return "redirect:/admin/users"; // Redirect to the users page after adding
    // }

    // @GetMapping("/user-details")
    // public String showUserDetails(@RequestParam("id") Long id, Model model) {
    // RegisterUser user = userService.getUserById(id);
    // if (user != null) {
    // model.addAttribute("user", user); // Add the user to the model for display
    // return "admin/user-details"; // Points to templates/admin/user-details.html
    // } else {
    // return "redirect:/admin/users"; // Redirect if user not found
    // }
    // }

    // @GetMapping("/user-bookings")
    // public String showUserBookings(@RequestParam("id") Long id, Model model) {
    // RegisterUser user = userService.getUserById(id);
    // if (user != null) {
    // List<Booking> bookings = bookingService.findBookingsByUserId(id); // Fetch
    // bookings for the user
    // model.addAttribute("user", user); // Add the user to the model for display
    // model.addAttribute("bookings", bookings); // Add bookings to the model for
    // display
    // return "admin/user-bookings"; // Points to templates/admin/user-bookings.html
    // } else {
    // return "redirect:/admin/users"; // Redirect if user not found
    // }
    // }

    // @GetMapping("/edit-room")
    // public String showEditRoomForm(@RequestParam("id") Long id, Model model) {
    // Room room = roomService.getRoomById(id);
    // if (room != null) {
    // List<Hotel> hotels = hotelService.getAllHotels(); // Fetch all hotels
    // model.addAttribute("room", room); // Add the room to the model for editing
    // model.addAttribute("hotels", hotels); // Add hotels to the model for display
    // return "admin/edit-room"; // Points to templates/admin/edit-room.html
    // } else {
    // return "redirect:/admin/rooms"; // Redirect if room not found
    // }
    // }

    // @PostMapping("/edit-room")
    // public String editRoom(@Valid @ModelAttribute("room") Room room,
    // BindingResult bindingResult,
    // RedirectAttributes redirectAttributes) {
    // if (bindingResult.hasErrors()) {
    // return "admin/edit-room"; // Return to the edit form if there are validation
    // errors
    // }
    // try {
    // roomService.updateRoom(room); // Assuming you have a method to update the
    // room
    // redirectAttributes.addFlashAttribute("successMessage", "Room updated
    // successfully!");
    // } catch (Exception e) {
    // redirectAttributes.addFlashAttribute("errorMessage", "Failed to update room:
    // " + e.getMessage());
    // }
    // return "redirect:/admin/rooms"; // Redirect to the rooms page after editing
    // }

}
