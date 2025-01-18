package com.neu.csye6220.parkmate.controller;

import com.neu.csye6220.parkmate.model.Rentee;
import com.neu.csye6220.parkmate.model.Renter;
import com.neu.csye6220.parkmate.model.User;
import com.neu.csye6220.parkmate.service.interfaces.IRenteeService;
import com.neu.csye6220.parkmate.service.interfaces.IRenterService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private IRenteeService renteeService;

    @Autowired
    private IRenterService renterService;

    @GetMapping
    public String getProfile(HttpSession session, Model model) {
        Integer renteeId = (Integer) session.getAttribute("renteeId");
        Integer renterId = (Integer) session.getAttribute("renterId");
        if (renterId == null && renteeId == null) {
            return "redirect:/login";
        }
        if (renterId != null) {
            Renter renter = renterService.findById(renterId);
            model.addAttribute("user", renter);
        }
        if (renteeId != null) {
            Rentee rentee = renteeService.findById(renteeId);
            model.addAttribute("user", rentee);
        }
        return "profile";
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute("user") User user, HttpSession session, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        try {
            // Disable caching
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            Integer renteeId = (Integer) session.getAttribute("renteeId");
            Integer renterId = (Integer) session.getAttribute("renterId");
            if (renterId == null && renteeId == null) {
                return "redirect:/login";
            }
            if (renterId != null) {
                Renter existingRenter = renterService.findById(renterId);
                existingRenter.setFirstName(user.getFirstName());
                existingRenter.setLastName(user.getLastName());
                existingRenter.setPhone(user.getPhone());
                Renter updatedRenter = renterService.save(existingRenter);
                redirectAttributes.addFlashAttribute("user", updatedRenter);
            }
            if (renteeId != null) {
                Rentee existingRentee = renteeService.findById(renteeId);
                existingRentee.setFirstName(user.getFirstName());
                existingRentee.setLastName(user.getLastName());
                existingRentee.setPhone(user.getPhone());
                Rentee updatedRentee = renteeService.save(existingRentee);
                redirectAttributes.addFlashAttribute("user", updatedRentee);
            }

            redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update profile. Please try again.");
        }
        return "redirect:/profile";
    }
}
