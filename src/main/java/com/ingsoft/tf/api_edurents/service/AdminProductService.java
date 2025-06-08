package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.StockDTO;

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

    List<ShowProductDTO> obtenerTop10ProductosPorVistas();

    List<ShowProductDTO> obtenerTop10ProductosPorCantidadDeIntercambios();

    List<ShowProductDTO> obtener10ProductosRecientes();

    void eliminarProducto(Integer id);

}