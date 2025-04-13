package com.mgcfgs.amritsartourism.amritsar_tourism.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.RegisterUser;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHomePage(HttpSession session, Model model) {
        RegisterUser user = (RegisterUser) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", user);
        return "home/index";
    }

    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        RegisterUser user = (RegisterUser) session.getAttribute("loggedInUser");
        // if (user == null) {
        //     return "redirect:/login";
        // }
        model.addAttribute("user", user);
        return "user/profile"; // create profile.html page in templates/user
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
