package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.UpdateProductDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.service.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class AdminProductController {

    private final AdminProductService adminProductService;

    @GetMapping
    public List<ShowProductDTO> obtenerProductos(){
        return adminProductService.obtenerTodosLosProductos();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ShowProductDTO crearProducto(@RequestBody ProductDTO productoDTO){
        return adminProductService.crearProducto(productoDTO);
    }

    //ACTUALIZAR CANTIDAD DE STOCK POR ID DE PRODUCTO
    @PutMapping("/{id}/updateStock")
    public UpdateProductDTO actualizarCantidadDisponible(@PathVariable Integer id, @RequestBody UpdateProductDTO dto) {
        return adminProductService.actualizarCantidadDisponible(id, dto.getCantidad_disponible());
    }

}
