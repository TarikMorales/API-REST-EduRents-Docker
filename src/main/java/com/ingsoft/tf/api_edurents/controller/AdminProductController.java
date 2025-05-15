package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.StockDTO;
import com.ingsoft.tf.api_edurents.dto.product.UpdateProductDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.service.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class AdminProductController {

    private final AdminProductService adminProductService;

    @GetMapping
    public ResponseEntity<List<ShowProductDTO>> obtenerProductos(){
        List<ShowProductDTO> productos = adminProductService.obtenerTodosLosProductos();
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<ShowProductDTO> crearProducto(@Valid @RequestBody ProductDTO productoDTO){
        ShowProductDTO producto = adminProductService.crearProducto(productoDTO);
        return new ResponseEntity<ShowProductDTO>(producto, HttpStatus.CREATED);
    }

    //ACTUALIZAR CANTIDAD DE STOCK POR ID DE PRODUCTO
    @PutMapping("/{id}/updateStock")
    public UpdateProductDTO actualizarCantidadDisponible(@PathVariable Integer id, @RequestBody UpdateProductDTO dto) {
        return adminProductService.actualizarCantidadDisponible(id, dto.getCantidad_disponible());
    }
    //MOSTRAR SOLO LA CANTIDAD DE STOCK POR ID DE PRODUCTO
    @GetMapping("/{id}/stock")
    public StockDTO obtenerStock(@PathVariable Integer id) {
        return adminProductService.obtenerStockProductoPorId(id);
    }

    @GetMapping("/{id}/fecha-expiracion")
    public ResponseEntity<ShowProductDTO> obtenerFechaExpiracion(@PathVariable Integer id) {
        ShowProductDTO dto = adminProductService.obtenerFechaExpiracion(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}/fecha-expiracion")
    public ResponseEntity<UpdateProductDTO> actualizarFechaExpiracion(
            @PathVariable Integer id,
            @RequestBody UpdateProductDTO request) {

        UpdateProductDTO updatedProduct = adminProductService.actualizarFechaExpiracion(id, request.getFecha_expiracion());
        return ResponseEntity.ok(updatedProduct);
    }

  
    @PutMapping("/{id}")
    public ResponseEntity<ShowProductDTO> editarProducto(@PathVariable Integer id, @Valid @RequestBody ProductDTO productoDTO){
        ShowProductDTO producto = adminProductService.editarProducto(id, productoDTO);
        return new ResponseEntity<ShowProductDTO>(producto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<ShowProductDTO> eliminarProducto(@PathVariable Integer id){
        adminProductService.eliminarProducto(id);
        return new ResponseEntity<ShowProductDTO>(HttpStatus.NO_CONTENT);
    }
}
