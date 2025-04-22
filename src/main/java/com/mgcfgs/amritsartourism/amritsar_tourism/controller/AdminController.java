package com.mgcfgs.amritsartourism.amritsar_tourism.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")  // Base path for all methods in this controller
public class AdminController {

    @GetMapping("/dashboard")  // Will map to /admin/dashboard
    public String showAdminPage() {
        return "admin/dashboard"; // Points to templates/admin/dashboard.html
    }

    // Additional admin endpoints can be added here
    @GetMapping("/users")
    public String showUsersPage() {
        return "admin/users"; // Would point to templates/admin/users.html
    }

    @GetMapping("/bookings")
    public String showHotelsPage() {
        return "admin/bookings"; // Would point to templates/admin/hotels.html
    }
}
