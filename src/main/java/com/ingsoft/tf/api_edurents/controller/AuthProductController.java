package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.service.AdminProductService;
import com.ingsoft.tf.api_edurents.service.AuthProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products/seller")
public class AuthProductController {
    private final AuthProductService authProductService;

    @GetMapping("/{sellerId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedor(@PathVariable Integer sellerId) {
        List<ShowProductDTO> productos = authProductService.obtenerProductosPorVendedor(sellerId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/{sellerId}/course/{courseId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedorYCurso(
            @PathVariable Integer sellerId, @PathVariable Integer courseId) {
        List<ShowProductDTO> productos = authProductService.obtenerProductosPorVendedorYCurso(sellerId, courseId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/{sellerId}/career/{careerId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedorYCarrera(
            @PathVariable Integer sellerId, @PathVariable Integer careerId) {
        List<ShowProductDTO> productos = authProductService.obtenerProductosPorVendedorYCarrera(sellerId, careerId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/{sellerId}/category/{categoryId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedorYCategoria(
            @PathVariable Integer sellerId, @PathVariable Integer categoryId) {
        List<ShowProductDTO> productos = authProductService.obtenerProductosPorVendedorYCategoria(sellerId, categoryId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }
}
