package com.mgcfgs.amritsartourism.amritsar_tourism.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.LoginUser;
import com.mgcfgs.amritsartourism.amritsar_tourism.model.RegisterUser;
import com.mgcfgs.amritsartourism.amritsar_tourism.service.UserServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	private UserServices userServices;

	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("user", new RegisterUser());
		return "user/register";
	}

	@PostMapping("/register")
	public String registerUser(
			@Valid @ModelAttribute("user") RegisterUser user,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {

		// 1. Return if there are validation errors (from @Valid annotations)
		if (result.hasErrors()) {
			return "user/register";
		}

		// 2. Check if passwords match
		if (!user.getPassword().equals(user.getConfirm_password())) {
			model.addAttribute("passwordError", "Passwords do not match");
			return "user/register";
		}

		// 3. Check if user already exists
		RegisterUser existingUser = userServices.findByEmail(user.getEmail());
		if (existingUser != null) {
			model.addAttribute("emailError", "User already exists with this email");
			return "user/register";
		}

		// 4. Save user
		userServices.saveUser(user);
		redirectAttributes.addFlashAttribute("message", "User registered successfully!");
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("user", new LoginUser());
		return "user/login"; // return the login form view
	}

	@PostMapping("/login")
	public String loginUser(@Valid @ModelAttribute("user") LoginUser user,
			BindingResult result,
			HttpSession session,
			RedirectAttributes redirectAttributes,
			Model model) {

		if (result.hasErrors()) {
			return "user/login"; // field validation errors
		}

		RegisterUser dbUser = userServices.findByEmail(user.getEmail());

		if (dbUser == null) {
			model.addAttribute("emailError", "User not found with this email");
			return "user/login";
		}

		if (!dbUser.getPassword().equals(user.getPassword())) {
			model.addAttribute("passwordError", "Incorrect password");
			return "user/login";
		}

		// Save full user object in session after successful login
		session.setAttribute("loggedInUser", dbUser);

		if (dbUser.getRole().equals("ADMIN")) {
			return "redirect:/admin"; // redirect to admin dashboard
		} else {
			redirectAttributes.addFlashAttribute("message", "Login successful!");
			return "redirect:/"; // redirect to home page

		}
	}

	@GetMapping("/delete-profile")
	public String deleteProfile(HttpSession session) {
		RegisterUser loggedInUser = (RegisterUser) session.getAttribute("loggedInUser");
		if (loggedInUser != null) {
			userServices.deleteUserById(loggedInUser.getId());
			session.invalidate(); // clear session after deletion
			return "redirect:/register?deleted"; // or home page
		}
		return "redirect:/login";
	}

}
