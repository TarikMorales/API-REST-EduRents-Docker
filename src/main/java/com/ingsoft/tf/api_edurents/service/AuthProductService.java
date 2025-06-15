package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;

import java.util.List;

public interface AuthProductService {
    List<ShowProductDTO> obtenerProductosPorVendedor(Integer idVendedor);
    List<ShowProductDTO> obtenerProductosPorVendedorYCurso(Integer idVendedor, Integer idCurso);
    List<ShowProductDTO> obtenerProductosPorVendedorYCarrera(Integer idVendedor, Integer idCarrera);
    List<ShowProductDTO> obtenerProductosPorVendedorYCategoria(Integer idVendedor, Integer idCategoria);
}