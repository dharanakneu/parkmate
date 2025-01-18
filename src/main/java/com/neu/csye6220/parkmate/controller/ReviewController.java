package com.neu.csye6220.parkmate.controller;

import com.neu.csye6220.parkmate.service.interfaces.IReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReviewController {

    @Autowired
    private IReviewService reviewService;

    @PostMapping("/rentee/submit-review")
    public String submitReview(@RequestParam("parkingLocationId") Integer parkingLocationId,
                               @RequestParam("rating") Integer rating,
                               @RequestParam("comment") String comment,
                               RedirectAttributes redirectAttributes,
                               HttpSession session) {
        Integer renteeId = (Integer) session.getAttribute("renteeId");
        try {
            reviewService.saveReview(parkingLocationId, rating, comment, renteeId) ;
            redirectAttributes.addFlashAttribute("successMessage", "Review submitted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to submit review. Please try again.");
        }
        return "redirect:/rentee/my-bookings";
    }
}
