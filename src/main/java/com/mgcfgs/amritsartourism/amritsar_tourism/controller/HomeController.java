package com.mgcfgs.amritsartourism.amritsar_tourism.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.Booking;
import com.mgcfgs.amritsartourism.amritsar_tourism.model.RegisterUser;
import com.mgcfgs.amritsartourism.amritsar_tourism.model.Room;
import com.mgcfgs.amritsartourism.amritsar_tourism.service.BookingService;
// import com.mgcfgs.amritsartourism.amritsar_tourism.service.RoomService;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    // private final RoomService roomService;
    private final BookingService bookingService;

    HomeController( BookingService bookingService) {
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
        // Add any necessary data to the model for the accommodation page
        model.addAttribute("booking", new Booking()); // Example model attribute
        // You can replace RegisterUser with the actual model class you want to use
        return "home/accommodation"; // create accommodation.html page in templates/home
    }

    @PostMapping("/accommodation")
    public String accommodationPost(@ModelAttribute Booking booking, HttpSession session) {
        List<Room> availableRooms = bookingService.findAvailableRooms(booking.getCheckIn(), booking.getCheckOut());

        if (availableRooms.isEmpty()) {
            // No rooms available
            return "redirect:/accommodation?error=no-rooms";
        }

        // 2. Pick the first available room
        Room selectedRoom = availableRooms.get(0);

        // 3. Set Room and User to Booking
        booking.setRoom(selectedRoom);

        RegisterUser user = (RegisterUser) session.getAttribute("loggedInUser");
        booking.setUser(user);

        // 4. Set other booking details
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("Confirmed");

        // 5. Save Booking
        bookingService.saveBooking(booking);

        return "redirect:/accommodation?success";

    }

    @GetMapping("/about")
    public String aboutPage() {
        return "home/about"; // create about.html page in templates/home
    }

}
