package com.mgcfgs.amritsartourism.amritsar_tourism.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.LoginUser;
import com.mgcfgs.amritsartourism.amritsar_tourism.model.RegisterUser;
import com.mgcfgs.amritsartourism.amritsar_tourism.repository.UserRepository;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("user", new RegisterUser());
		return "user/registerForm";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") RegisterUser user, Model model) {
		userRepository.save(user);
		model.addAttribute("message", "User registered successfully!");
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("user", new LoginUser());
		return "user/loginForm"; // return the login form view
	}

	@PostMapping("/login")
	public String loginUser(@RequestParam String email,
			@RequestParam String password,
			Model model) {
		RegisterUser user = userRepository.findByEmail(email);

		if (user != null && user.getPassword().equals(password)) {
			return "home/index"; // successful login
		} else {
			model.addAttribute("error", "Invalid email or password");
			return "user/loginForm"; // go back to login page with error
		}
	}

}
