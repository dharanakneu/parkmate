package com.neu.csye6220.parkmate.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Retrieve the session
        HttpSession session = request.getSession(false); // Get session if it exists, don't create a new one
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false; // Block the request
        }

        // Check if the session contains either renterId or renteeId
        Integer renterId = (Integer) session.getAttribute("renterId");
        Integer renteeId = (Integer) session.getAttribute("renteeId");

        String requestURI = request.getRequestURI();

        if (renterId != null) {
            // Validate routes accessible to renters
            if (!requestURI.startsWith(request.getContextPath() + "/renter")) {
                response.sendRedirect(request.getContextPath() + "/access-denied");
                return false;
            }
        } else if (renteeId != null) {
            // Validate routes accessible to rentees
            if (!requestURI.startsWith(request.getContextPath() + "/rentee")) {
                response.sendRedirect(request.getContextPath() + "/access-denied");
                return false;
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        return true;
    }
}

