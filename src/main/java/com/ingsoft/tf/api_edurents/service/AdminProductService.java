package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.StockDTO;
import com.ingsoft.tf.api_edurents.dto.product.UpdateProductDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;

import java.util.List;

public interface AdminProductService {

    List<ShowProductDTO> obtenerTodosLosProductos();

    ShowProductDTO crearProducto(ProductDTO productoDTO);

    StockDTO obtenerStockProductoPorId(Integer idProducto);
    UpdateProductDTO actualizarCantidadDisponible(Integer id, Integer nuevaCantidad);
}
