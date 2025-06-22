package com.javatechie.crud.example.controller;

import com.javatechie.crud.example.entity.Product;
import com.javatechie.crud.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/addProduct")
    public Product addProduct(@RequestBody Product product) {
        return service.saveProduct(product);
    }

    @PostMapping("/addProducts")
    public List<Product> addProducts(@RequestBody List<Product> products) {
        return service.saveProducts(products);
    }

    @GetMapping("/products")
    public List<Product> findAllProducts() {
        return service.getProducts();
    }

    @GetMapping("/productById/{id}")
    public Product findProductById(@PathVariable int id) {
        return service.getProductById(id);
    }

    @GetMapping("/product/{name}")
    public Product findProductByName(@PathVariable String name) {
        return service.getProductByName(name);
    }

    @PutMapping("/update")
    public Product updateProduct(@RequestBody Product product) {
        return service.updateProduct(product);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        return service.deleteProduct(id);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Product Service is up and running");
    }

    // Advanced Search (v2.0.0)
    @GetMapping("/products/search")
    public ResponseEntity<?> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        List<Product> products = service.getProducts();

        List<Product> filtered = products.stream()
                .filter(p -> {
                    boolean matches = true;
                    if (keyword != null && !keyword.isEmpty()) {
                        matches = matches && p.getName().toLowerCase().contains(keyword.toLowerCase());
                    }
                    if (minPrice != null) {
                        matches = matches && p.getPrice() >= minPrice;
                    }
                    if (maxPrice != null) {
                        matches = matches && p.getPrice() <= maxPrice;
                    }
                    return matches;
                })
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            return ResponseEntity.status(404).body("No products found for the given search criteria.");
        }

        return ResponseEntity.ok(filtered);
    }
}
