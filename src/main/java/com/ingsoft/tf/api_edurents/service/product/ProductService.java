package com.ingsoft.tf.api_edurents.service.product;

import com.ingsoft.tf.api_edurents.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getFilteredProducts(Long carreraId, Long cursoID);
    List<ProductDTO> getAllProducts();
}
