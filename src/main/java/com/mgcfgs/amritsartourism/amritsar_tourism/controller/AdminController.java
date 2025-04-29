package com.mgcfgs.amritsartourism.amritsar_tourism.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.Booking;
import com.mgcfgs.amritsartourism.amritsar_tourism.model.RegisterUser;
import com.mgcfgs.amritsartourism.amritsar_tourism.model.Room;
import com.mgcfgs.amritsartourism.amritsar_tourism.service.BookingService;
import com.mgcfgs.amritsartourism.amritsar_tourism.service.RoomService;
import com.mgcfgs.amritsartourism.amritsar_tourism.service.UserServices;

@Controller
@RequestMapping("/admin") // Base path for all methods in this controller
public class AdminController {

    @Autowired
    UserServices registerUserService; // Assuming this service is used for user management

    @Autowired
    RoomService roomService; // Assuming this service is used for room management

    @Autowired
    BookingService bookingService; // Assuming this service is used for booking management

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
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings); // Add bookings to the model for display
        return "admin/bookings"; // Would point to templates/admin/hotels.html
    }

    @GetMapping("/addroom")
    public String showAddRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "admin/addroom";
    }

    @PostMapping("/addroom")
    public String addRoom(@ModelAttribute Room room) {
        room.setAvailable(true); // Default to available
        roomService.saveRoom(room);
        return "redirect:/admin/rooms"; // Redirect to room list
    }

    @GetMapping("/rooms")
    public String viewRooms(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "admin/managerooms";
    }
}
