package com.haxquarium.controller;

import com.haxquarium.model.User;
import com.haxquarium.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller for authentication-related operations.
 */
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    
    /**
     * Display the login page.
     * 
     * @return The name of the view to render
     */
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
    
    /**
     * Display the registration page.
     * 
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }
    
    /**
     * Process a registration request.
     * 
     * @param user The user to register
     * @param result The binding result
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
    @PostMapping("/register")
    public String registerSubmit(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        // Check for validation errors
        if (result.hasErrors()) {
            return "auth/register";
        }
        
        // Check if username already exists
        if (userService.existsByUsername(user.getUsername())) {
            model.addAttribute("usernameError", "Username already exists");
            return "auth/register";
        }
        
        // Check if email already exists
        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("emailError", "Email already exists");
            return "auth/register";
        }
        
        // Register the user
        userService.register(user);
        
        return "redirect:/login?registered";
    }
}
