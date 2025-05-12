package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.ProductDTO;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.CategoriesProducts;
import com.ingsoft.tf.api_edurents.repository.product.CategoriesProductsRepository;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getProductsByCategory(Integer category_id) {
        List<CategoriesProducts> relations = CategoriesProductsRepository.findByCategoriaId(category_id);
        return null;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return ProductMapper.toDTOs(productRepository.findAll());
    }
}
