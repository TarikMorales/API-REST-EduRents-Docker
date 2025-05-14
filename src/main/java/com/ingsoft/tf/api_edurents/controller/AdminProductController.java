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

   // @PutMapping("/{id}/fecha-expiracion")
   // public UpdateProductDTO actualizarFechaExpiracion(
   //         @PathVariable Integer id,
   //         @RequestBody UpdateProductDTO request) {
   //     return adminProductService.actualizarFechaExpiracion(id, request.getFecha_expiracion());
  //  }

     @PutMapping("/{id}/fecha-expiracion")
      public UpdateProductDTO actualizarFechaExpiracion(@PathVariable Integer id, @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
         return adminProductService.actualizarFechaExpiracion(id, fecha);
     }
}
