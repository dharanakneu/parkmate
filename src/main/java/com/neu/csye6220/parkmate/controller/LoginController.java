package com.neu.csye6220.parkmate.controller;

import com.neu.csye6220.parkmate.dto.LoginDTO;
import com.neu.csye6220.parkmate.dto.RegisterDTO;
import com.neu.csye6220.parkmate.service.interfaces.IRenteeService;
import com.neu.csye6220.parkmate.service.interfaces.IRenterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.neu.csye6220.parkmate.model.Rentee;
import com.neu.csye6220.parkmate.model.Renter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private IRenterService renterService;

    @Autowired
    private IRenteeService renteeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new RegisterDTO());
        return "register";
    }

    @PostMapping("/register/renter")
    public String registerRenter(@Valid @ModelAttribute("user") RegisterDTO user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        if (renterService.emailExistsInAnyTable(user.getEmail())) {
            model.addAttribute("errorMessages", "Email already exists! Use a different email.");
            return "register";
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        Renter renter = new Renter();
        renter.setFirstName(user.getFirstName());
        renter.setLastName(user.getLastName());
        renter.setEmail(user.getEmail());
        renter.setPassword(encodedPassword);
        renter.setPhone(user.getPhone());
        renterService.registerRenter(renter);
        return "redirect:/login";
    }

    @PostMapping("/register/rentee")
    public String registerRentee(@Valid @ModelAttribute("user") RegisterDTO user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        if (renterService.emailExistsInAnyTable(user.getEmail())) {
            model.addAttribute("errorMessages", "Email already exists! Use a different email.");
            return "register";
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        Rentee rentee = new Rentee();
        rentee.setFirstName(user.getFirstName());
        rentee.setLastName(user.getLastName());
        rentee.setEmail(user.getEmail());
        rentee.setPassword(encodedPassword);
        rentee.setPhone(user.getPhone());
        renteeService.registerRentee(rentee);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new LoginDTO());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("user") LoginDTO user, BindingResult result, HttpServletRequest request, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "login";
        }

        // Session fixation protection
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Invalidate old session
        }
        HttpSession newSession = request.getSession(true);

        int renterId = renterService.authenticate(user.getEmail(), user.getPassword());
        if (renterId > 0) {
            newSession.setAttribute("renterId", renterId);
            newSession.setAttribute("role", "renter");

            return "redirect:/renter/post-parking";
        }

        int renteeId = renteeService.authenticate(user.getEmail(), user.getPassword());
        if (renteeId > 0) {
            newSession.setAttribute("renteeId", renteeId);
            newSession.setAttribute("role", "rentee");
            return "redirect:/rentee/parking-locations?lat=0&lon=0&radius=2";
        }

        model.addAttribute("user", new LoginDTO());
        model.addAttribute("errorMessages", "Invalid email or password.");
        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

