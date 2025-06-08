package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.StockDTO;
import org.springframework.http.codec.ServerSentEventHttpMessageWriter;

import java.time.LocalDate;
import java.util.List;

public interface AdminProductService {

    List<ShowProductDTO> obtenerTodosLosProductos();

    ShowProductDTO crearProducto(ProductDTO productoDTO);

    StockDTO obtenerStockProductoPorId(Integer idProducto);

    ShowProductDTO actualizarCantidadDisponible(Integer id, Integer nuevaCantidad);

    ShowProductDTO actualizarFechaExpiracion (Integer id, LocalDate nuevaFecha);
  
    ShowProductDTO obtenerFechaExpiracion(Integer id);
  
    ShowProductDTO editarProducto(Integer id, ProductDTO productoDTO);

    List<ShowProductDTO> obtenerProductosPorVendedor(Integer idVendedor);

    List<ShowProductDTO> obtenerProductosPorCursoYCarrera(Integer idCarrera, Integer idCurso);

    List<ShowProductDTO> obtenerProductosPorCategoria(Integer idCategoria);

    void sumarView(Integer id);

    List<ShowProductDTO> obtenerProductosRecomendados(Integer idCareer);

    List<ShowProductDTO> obtenerProductosPorCarreraOrdenarPorVistas(Integer idCareer);

    List<ShowProductDTO> obtenerProductosPorCursoOrdenarPorVistas(Integer idCourse);

    List<ShowProductDTO> obtenerProductosPorCarreraPorCursoOrdenarPorVistas(Integer idCareer, Integer idCourse);

    List<ShowProductDTO> obtenerProductosPorCategoriaOrdernarPorVistas(Integer idCategory);

    List<ShowProductDTO> obtenerProductosPorCarreraPorCursoPorCategoriaOrdenarPorVistas(Integer idCareer, Integer idCourse, Integer idCategory);

    List<ShowProductDTO> obtenerProductosPorIdVendedorOrdenarPorVistas(Integer idSeller);

    void eliminarProducto(Integer id);

}