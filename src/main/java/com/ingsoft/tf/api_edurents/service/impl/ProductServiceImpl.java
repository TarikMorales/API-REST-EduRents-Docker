package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.ProductDTO;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.service.product.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return ProductMapper.toDTOs(productRepository.findAll());
    }

    @Override
    public List<ProductDTO> getAllProductsBySellerId(Integer sellerId) {
        return ProductMapper.toDTOs(productRepository.findByVendedor_Id(sellerId));
    }
}
