package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.StockDTO;
import com.ingsoft.tf.api_edurents.service.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/{id}/update-stock")
    public ShowProductDTO actualizarCantidadDisponible(@PathVariable Integer id, @RequestBody Integer cantidad) {
        return adminProductService.actualizarCantidadDisponible(id, cantidad);
    }
    //MOSTRAR SOLO LA CANTIDAD DE STOCK POR ID DE PRODUCTO
    @GetMapping("/{id}/stock")
    public StockDTO obtenerStock(@PathVariable Integer id) {
        return adminProductService.obtenerStockProductoPorId(id);
    }

    @GetMapping("/{id}/expiration-date")
    public ResponseEntity<ShowProductDTO> obtenerFechaExpiracion(@PathVariable Integer id) {
        ShowProductDTO dto = adminProductService.obtenerFechaExpiracion(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}/expiration-date")
    public ResponseEntity<ShowProductDTO> actualizarFechaExpiracion(
            @PathVariable Integer id,
            @RequestBody ProductDTO request) {

        ShowProductDTO updatedProduct = adminProductService.actualizarFechaExpiracion(id, request.getFecha_expiracion());
        return ResponseEntity.ok(updatedProduct);
    }

  
    @PutMapping("/{id}")
    public ResponseEntity<ShowProductDTO> editarProducto(@PathVariable Integer id, @Valid @RequestBody ProductDTO productoDTO){
        ShowProductDTO producto = adminProductService.editarProducto(id, productoDTO);
        return new ResponseEntity<ShowProductDTO>(producto, HttpStatus.OK);
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedor(@PathVariable Integer sellerId) {
        List<ShowProductDTO> productos = adminProductService.obtenerProductosPorVendedor(sellerId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/career/{carreraId}/course/{cursoId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCursoYCarrera(@PathVariable Integer carreraId, @PathVariable Integer cursoId) {
        List<ShowProductDTO> productos = adminProductService.obtenerProductosPorCursoYCarrera(carreraId, cursoId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCategoria(@PathVariable Integer categoryId) {
        List<ShowProductDTO> productos = adminProductService.obtenerProductosPorCategoria(categoryId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/career/{idCareer}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCarrera(@PathVariable Integer idCareer) {
        List<ShowProductDTO> productos = adminProductService.obtenerProductosPorCarrera(idCareer);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/career/{idCourse}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCurso(@PathVariable Integer idCourse) {
        List<ShowProductDTO> productos = adminProductService.obtenerProductosPorCurso(idCourse);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("career/{idCareer}/course/{idCourse}/category/{idCategory}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCarreraCursoCategoria(@PathVariable Integer idCareer, @PathVariable Integer idCourse, @PathVariable Integer idCategory) {
        List<ShowProductDTO> productos = adminProductService.obtenerProductosPorCarreraCursoCategoria(idCareer, idCourse, idCategory);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<ShowProductDTO> eliminarProducto(@PathVariable Integer id){
        adminProductService.eliminarProducto(id);
        return new ResponseEntity<ShowProductDTO>(HttpStatus.NO_CONTENT);
    }
}
