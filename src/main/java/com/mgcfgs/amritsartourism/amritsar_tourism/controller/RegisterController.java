package com.mgcfgs.amritsartourism.amritsar_tourism.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.User;
import com.mgcfgs.amritsartourism.amritsar_tourism.repository.UserRepository;

@Controller
public class RegisterController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("user", new User());
		return "user/registerForm";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") User user, Model model) {
		userRepository.save(user);
		model.addAttribute("message", "User registered successfully!");
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		return "user/loginForm";
	}
	
	
	
}
