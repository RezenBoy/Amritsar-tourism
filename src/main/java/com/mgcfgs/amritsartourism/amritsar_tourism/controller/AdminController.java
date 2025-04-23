package com.mgcfgs.amritsartourism.amritsar_tourism.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.RegisterUser;
import com.mgcfgs.amritsartourism.amritsar_tourism.service.UserServices;

@Controller
@RequestMapping("/admin")  // Base path for all methods in this controller
public class AdminController {

    @Autowired
    UserServices registerUserService; // Assuming this service is used for user management

    @GetMapping("/dashboard")  // Will map to /admin/dashboard
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
    public String showHotelsPage() {
        return "admin/bookings"; // Would point to templates/admin/hotels.html
    }
}
