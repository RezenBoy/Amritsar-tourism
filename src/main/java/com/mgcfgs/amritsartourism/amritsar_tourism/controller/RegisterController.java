package com.mgcfgs.amritsartourism.amritsar_tourism.controller;

// import com.mgcfgs.amritsartourism.amritsar_tourism.model.User;
// import com.mgcfgs.amritsartourism.amritsar_tourism.repository.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    // @Autowired
    // private UserRepository userRepository;

    @GetMapping("/register")
    public String registerPage() {
        // model.addAttribute("user", new User());
        return "user/registerForm";
    }

    // @PostMapping("/register")
    // public String registerUser(@ModelAttribute("user") User user, Model model) {
    //     userRepository.save(user);
    //     model.addAttribute("message", "User registered successfully!");
    //     return "user/registerForm";
    // }
}
