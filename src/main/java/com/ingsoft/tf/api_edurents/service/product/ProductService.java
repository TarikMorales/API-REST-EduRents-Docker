package com.ingsoft.tf.api_edurents.service.product;

import com.ingsoft.tf.api_edurents.dto.ProductDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findByCategoryId(Integer categoryId);
    List<ProductDTO> getFilteredProducts(Integer carreraId, Integer cursoID);
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getAllProductsBySellerId(Integer sellerId);
}
