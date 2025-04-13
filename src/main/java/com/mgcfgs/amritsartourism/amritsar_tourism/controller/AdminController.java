package com.mgcfgs.amritsartourism.amritsar_tourism.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    // This class is responsible for handling admin-related actions, such as
    // managing
    // users and viewing reports.
    // You can add methods to handle admin-specific actions here.
    // For example, you can create methods to view all users, delete users, etc.
    // You can also create methods to view reports, such as user activity reports,
    // booking reports, etc.
    // You can use the UserRepository to interact with the database and perform CRUD

    // operations on the User entity.
    // You can also use the Model and View classes to render the admin pages and
    // pass
    // data to the views.
    @GetMapping("/admin")
    public String showAdminPage() {
        return "admin/dashboard"; // This assumes you have a template at templates/admin/dashboard.html
    }

    // If you want another method with a @RequestParam, define it like this:
    @GetMapping("/admin/handle")
    public String handleParam(@RequestParam String param, Model model) {
        model.addAttribute("param", param);
        return "admin/handle"; // Return a corresponding view
    }

}
