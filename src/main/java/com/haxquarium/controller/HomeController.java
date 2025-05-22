package com.haxquarium.controller;

import com.haxquarium.model.Product;
import com.haxquarium.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Controller for the home page.
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    
    /**
     * Display the home page.
     * 
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
    @GetMapping({"/", "/home"})
    public String home(Model model) {
        List<Product> featuredProducts = productService.findAll();
        model.addAttribute("featuredProducts", featuredProducts);
        return "home";
    }
    
    /**
     * Display the about page.
     * 
     * @return The name of the view to render
     */
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
