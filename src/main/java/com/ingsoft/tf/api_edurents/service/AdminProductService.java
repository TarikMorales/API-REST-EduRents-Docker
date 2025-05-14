package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;

import java.util.List;

public interface AdminProductService {

    List<ShowProductDTO> obtenerTodosLosProductos();

    ShowProductDTO crearProducto(ProductDTO productoDTO);

    ShowProductDTO editarProducto(Integer id, ProductDTO productoDTO);

    void eliminarProducto(Integer id);

}
