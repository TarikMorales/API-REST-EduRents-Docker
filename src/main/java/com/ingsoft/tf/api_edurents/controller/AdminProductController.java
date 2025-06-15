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

    @GetMapping("/{id}")
    public ResponseEntity<ShowProductDTO> obtenerProductoPorId(@PathVariable Integer id){
        ShowProductDTO producto = adminProductService.obtenerProductoPorId(id);
        return new ResponseEntity<ShowProductDTO>(producto, HttpStatus.OK);
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
        List<ShowProductDTO> productos = adminProductService.obtenerProductosPorCarreraCursoYCategoria(idCareer, idCourse, idCategory);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<ShowProductDTO> eliminarProducto(@PathVariable Integer id){
        adminProductService.eliminarProducto(id);
        return new ResponseEntity<ShowProductDTO>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/products/trendy")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosTrendy(){
        List<ShowProductDTO> productos = adminProductService.obtenerTop10ProductosPorVistas();
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/products/top-exchanges")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosTopExchange(){
        List<ShowProductDTO> productos = adminProductService.obtenerTop10ProductosPorCantidadDeIntercambios();
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/products/recents")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosRecents(){
        List<ShowProductDTO> productos = adminProductService.obtener10ProductosRecientes();
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @PutMapping("/{idProduct}/views")
    public ResponseEntity<ShowProductDTO> sumarViews(@PathVariable Integer idProduct){
        adminProductService.sumarView(idProduct);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/products/recomendados/{idCareer}")
    public ResponseEntity<List<ShowProductDTO>> recomendarProductos(@PathVariable Integer idCareer){
        adminProductService.obtenerProductosRecomendados(idCareer);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/products/career/{idCareer}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCareerOrderByView(@PathVariable Integer idCareer){
        adminProductService.obtenerProductosPorCarreraOrdenarPorVistas(idCareer);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/products/course/{idCourse}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCourseOrderByView(@PathVariable Integer idCourse){
        adminProductService.obtenerProductosPorCursoOrdenarPorVistas(idCourse);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/products/course/{idCourse}/career/{idCareer}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCareerCourseOrderByView(@PathVariable Integer idCareer, @PathVariable Integer idCourse){
        adminProductService.obtenerProductosPorCarreraPorCursoOrdenarPorVistas(idCareer, idCourse);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/products/category/{idCategory}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCategoryOrderByView(@PathVariable Integer idCategory){
        adminProductService.obtenerProductosPorCategoriaOrdernarPorVistas(idCategory);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/products/career/{idCareer}/course/{idCourse}/category/{idCategory}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCareerCourseCategoryOrderByView(@PathVariable Integer idCareer, @PathVariable Integer idCourse, @PathVariable Integer idCategory){
        adminProductService.obtenerProductosPorCarreraPorCursoPorCategoriaOrdenarPorVistas(idCareer, idCourse, idCategory);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/products/views/seller/{idSeller}")
    public ResponseEntity<List<ShowProductDTO>> ProductSellerOrderByView(@PathVariable Integer idSeller){
        adminProductService.obtenerProductosPorIdVendedorOrdenarPorVistas(idSeller);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

}
