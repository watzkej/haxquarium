package com.haxquarium.controller;

import com.haxquarium.model.Product;
import com.haxquarium.model.User;
import com.haxquarium.service.ProductService;
import com.haxquarium.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Controller for admin-related operations.
 * This controller contains intentional vulnerabilities for training purposes.
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final ProductService productService;
    
    /**
     * Display the admin dashboard.
     * 
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
    @GetMapping
    public String dashboard(Model model) {
        List<User> users = userService.findAll();
        List<Product> products = productService.findAll();
        
        model.addAttribute("users", users);
        model.addAttribute("products", products);
        
        return "admin/dashboard";
    }
    
    /**
     * Display the user management page.
     * 
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
    @GetMapping("/users")
    public String manageUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/users";
    }
    
    /**
     * Display the product management page.
     * 
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
    @GetMapping("/products")
    public String manageProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin/products";
    }
    
    /**
     * Display the form to add a new product.
     * 
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
    @GetMapping("/products/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product-form";
    }
    
    /**
     * Display the form to edit a product.
     * 
     * @param id The ID of the product
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
        
        model.addAttribute("product", product);
        return "admin/product-form";
    }
    
    /**
     * Save a product.
     * 
     * @param product The product to save
     * @return A redirect to the product management page
     */
    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/admin/products";
    }
    
    /**
     * Delete a product.
     * 
     * @param id The ID of the product to delete
     * @return A redirect to the product management page
     */
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }
    
    /**
     * Execute a system command.
     * This endpoint is intentionally vulnerable to command injection.
     * 
     * @param command The command to execute
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
    @PostMapping("/execute")
    public String executeCommand(@RequestParam String command, Model model) {
        try {
            // Intentionally vulnerable to command injection
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            
            model.addAttribute("output", output.toString());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        
        return "admin/command-output";
    }
}
