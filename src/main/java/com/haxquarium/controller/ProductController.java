package com.haxquarium.controller;

import com.haxquarium.model.Product;
import com.haxquarium.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controller for product-related operations.
 */
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    
    /**
     * Display all products.
     * 
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "products/list";
    }
    
    /**
     * Display products by category.
     * 
     * @param categoryId The ID of the category
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
    @GetMapping("/category/{categoryId}")
    public String listProductsByCategory(@PathVariable Long categoryId, Model model) {
        List<Product> products = productService.findByCategoryId(categoryId);
        model.addAttribute("products", products);
        return "products/list";
    }
    
    /**
     * Display a single product.
     * 
     * @param id The ID of the product
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
        
        model.addAttribute("product", product);
        return "products/view";
    }
    
    /**
     * Search for products.
     * 
     * @param keyword The keyword to search for
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
    @GetMapping("/search")
    public String searchProducts(@RequestParam String keyword, Model model) {
        List<Product> products = productService.search(keyword);
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        return "products/search-results";
    }
    
    /**
     * Search for products using a vulnerable SQL query.
     * This endpoint is intentionally vulnerable to SQL injection.
     * 
     * @param query The search query
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
    @GetMapping("/search-vulnerable")
    public String searchProductsVulnerable(@RequestParam String query, Model model) {
        List<Product> products = productService.searchVulnerable(query);
        model.addAttribute("products", products);
        model.addAttribute("query", query);
        return "products/search-results";
    }
}
