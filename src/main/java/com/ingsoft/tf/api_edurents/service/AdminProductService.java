package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.StockDTO;
import com.ingsoft.tf.api_edurents.dto.product.UpdateProductDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;

import java.time.LocalDate;
import java.util.List;

public interface AdminProductService {

    List<ShowProductDTO> obtenerTodosLosProductos();

    ShowProductDTO crearProducto(ProductDTO productoDTO);

    StockDTO obtenerStockProductoPorId(Integer idProducto);
  
    UpdateProductDTO actualizarCantidadDisponible(Integer id, Integer nuevaCantidad);

    UpdateProductDTO actualizarFechaExpiracion (Integer id, LocalDate nuevaFecha);
  
    ShowProductDTO obtenerFechaExpiracion(Integer id);
  
    ShowProductDTO editarProducto(Integer id, ProductDTO productoDTO);

    List<ShowProductDTO> obtenerProductosPorVendedor(Integer idVendedor);

    List<ShowProductDTO> obtenerProductosPorCursoYCarrera(Integer idCarrera, Integer idCurso);

    List<ShowProductDTO> obtenerProductosPorCategoria(Integer idCategoria);

    void eliminarProducto(Integer id);

}