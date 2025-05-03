package com.mgcfgs.amritsartourism.amritsar_tourism.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.Booking;
import com.mgcfgs.amritsartourism.amritsar_tourism.model.Hotel;
import com.mgcfgs.amritsartourism.amritsar_tourism.model.RegisterUser;
import com.mgcfgs.amritsartourism.amritsar_tourism.model.Room;
import com.mgcfgs.amritsartourism.amritsar_tourism.service.BookingService;
import com.mgcfgs.amritsartourism.amritsar_tourism.service.HotelService;

// import com.mgcfgs.amritsartourism.amritsar_tourism.service.RoomService;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    // private final RoomService roomService;
    private final BookingService bookingService;
    private final HotelService hotelService;
    // private final UserServices userService; // Assuming this service is used for
    // user management

    HomeController(BookingService bookingService, HotelService hotelService) {
        this.hotelService = hotelService;
        this.bookingService = bookingService;
        // this.roomService = roomService;
    }

    @GetMapping("/")
    public String showHomePage(HttpSession session, Model model) {
        RegisterUser user = (RegisterUser) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", user);
        return "home/index";
    }

    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        RegisterUser user = (RegisterUser) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "user/profile"; // create profile.html page in templates/user
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

   @GetMapping("/accommodation")
    public String accommodationPage(Model model) {
        List<Hotel> hotels = hotelService.getAllHotels();
        model.addAttribute("hotels", hotels);
        model.addAttribute("booking", new Booking());
        return "home/accommodation"; // Matches the location of accommodation.html
    }

    @PostMapping("/accommodation")
    public String accommodationPost(
            @ModelAttribute("booking") Booking booking,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            // 1. Parse the check-in and check-out dates from String to LocalDate
            LocalDate checkIn = booking.getCheckIn();
            LocalDate checkOut = booking.getCheckOut();

            // 2. Validate dates (server-side)
            if (checkIn.isAfter(checkOut)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Check-in date must be before check-out date.");
                return "redirect:/accommodation?status=error";
            }

            // 3. Find available rooms for the selected hotel
            List<Room> availableRooms = bookingService.findAvailableRooms(
                    checkIn,
                    checkOut,
                    booking.getHotelName());

            if (availableRooms.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "No rooms available for the selected dates.");
                return "redirect:/accommodation?status=error";
            }

            // 4. Pick the first available room (temporary; consider letting user choose)
            Room selectedRoom = availableRooms.get(0);
            booking.setRoom(selectedRoom);

            // 5. Set the user from the session
            RegisterUser user = (RegisterUser) session.getAttribute("loggedInUser");
            if (user == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Please log in to make a booking.");
                return "redirect:/login";
            }
            booking.setUser(user);

            // 6. Set other booking details
            booking.setBookingDate(LocalDateTime.now());
            booking.setStatus("Confirmed");

            // 7. Save the booking
            bookingService.saveBooking(booking);

            redirectAttributes.addFlashAttribute("successMessage",
                    "Booking confirmed for " + booking.getHotelName());
            return "redirect:/accommodation?status=success";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "An error occurred while processing your booking: " + e.getMessage());
            return "redirect:/accommodation?status=error";
        }
    }
    @GetMapping("/about")
    public String aboutPage() {
        return "home/about"; // create about.html page in templates/home
    }

}
