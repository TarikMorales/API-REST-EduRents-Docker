package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.ProductDTO;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{carreraId}/{cursoId}")
    public List<ProductDTO> getFilteredProducts(@PathVariable Integer carreraId, @PathVariable Integer cursoId) {
        return productService.getFilteredProducts(carreraId, cursoId);
    }
  
    @GetMapping("/seller/{sellerId}")
    public List<ProductDTO> getAllProductsBySellerId(@PathVariable Integer sellerId) {
        return productService.getAllProductsBySellerId(sellerId);
    }
}
