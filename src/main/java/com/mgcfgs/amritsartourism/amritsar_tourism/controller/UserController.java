package com.mgcfgs.amritsartourism.amritsar_tourism.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.LoginUser;
import com.mgcfgs.amritsartourism.amritsar_tourism.model.RegisterUser;
import com.mgcfgs.amritsartourism.amritsar_tourism.service.UserServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

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
			// model.addAttribute("loginSuccess", true);
			// model.addAttribute("loginMessage", "Welcome back!"); // optional
			return "redirect:/admin/dashboard"; // redirect to admin dashboard
		} else {
			// model.addAttribute("loginSuccess", true);
			// model.addAttribute("loginMessage", "Welcome back!"); // optional
			redirectAttributes.addFlashAttribute("message", "Login successful!");
			return "redirect:/"; // redirect to home page

		}
	}

	@GetMapping("/open")
	public String open(HttpSession session, Model model) {
		RegisterUser user = (RegisterUser) session.getAttribute("loggedInUser");

		if (user != null) {
			model.addAttribute("user", user);
		}

		return "user/updateProfile";
	}

	@PostMapping("/update")
	public String update(RegisterUser user, Model model, HttpSession session) {

		userServices.saveUser(user);
		session.setAttribute("loggedInUser", user);
		return "redirect:/profile";
	}

	@PostMapping("/profile/update-profile")
	public String updateProfile(
			@RequestParam @NotBlank String name,
			@RequestParam @Email @NotBlank String email,
			HttpSession session,
			RedirectAttributes redirectAttributes) {

		try {
			RegisterUser user = (RegisterUser) session.getAttribute("loggedInUser");
			if (user == null) {
				return "redirect:/login";
			}

			userServices.updateUserProfile(user.getId(), name, email);

			// Update session with new data
			user.setName(name);
			user.setEmail(email);
			session.setAttribute("loggedInUser", user);

			redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error updating profile: " + e.getMessage());
		}

		return "redirect:/profile";
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

	@GetMapping("/bookings")
	public String bookingsPage(HttpSession session, Model model) {
		RegisterUser user = (RegisterUser) session.getAttribute("loggedInUser");
		if (user == null) {
			return "redirect:/login";
		}
		model.addAttribute("user", user);
		return "user/bookings"; // create bookings.html page in templates/user
	}

}
