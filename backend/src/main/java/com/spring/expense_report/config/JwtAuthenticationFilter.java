package com.spring.expense_report.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.expense_report.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // Get JWT token from Authorization header
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // Check if header exists and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extract the JWT token from the header (remove "Bearer ")
            token = authHeader.substring(7);

            // Validate the token and extract username
            if (jwtUtil.validateToken(token)) {
                username = jwtUtil.extractUsername(token);
            }

            // Debugging (optional)
            // System.err.println("Token is valid: " + jwtUtil.validateToken(token));
            // System.err.println("Extracted username: " + username);
        }

        // If a username is found and the user is not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details from the database (or your custom user service)
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validate token again (extra security)
            if (jwtUtil.validateToken(token)) {
                // Create an authentication object with the user's details and roles
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Set the authentication in the SecurityContext so Spring Security knows the user is authenticated
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue the filter chain (pass the request to the next filter or controller)
        filterChain.doFilter(request, response);
    }
}
