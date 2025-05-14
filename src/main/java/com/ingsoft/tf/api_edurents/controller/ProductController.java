package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.ProductDTO;
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
    private final ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/product/{carreraId}/{cursoID}")
    public List<ProductDTO> getFilteredProducts(@PathVariable Integer carreraId, @PathVariable Integer cursoID) {
        return productService.getFilteredProducts(carreraId, cursoID);
    }
    // Manejo de excepciones
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        // Aquí puedes personalizar el mensaje de error
        String errorMessage = ex.getMessage();  // Obtiene el mensaje de la excepción
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND); // Devuelve 404 con el mensaje de error
    }
}
