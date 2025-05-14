package com.ingsoft.tf.api_edurents.service.product;

import com.ingsoft.tf.api_edurents.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getFilteredProducts(Integer carreraId, Integer cursoID);
    List<ProductDTO> getAllProducts();
}
