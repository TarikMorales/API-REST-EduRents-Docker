package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.ProductDTO;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.repository.user.SellerRepository;
import com.ingsoft.tf.api_edurents.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private final ProductRepository productRepository;
  
    @Autowired
    private final SellerRepository sellerRepository;

    public ProductServiceImpl(ProductRepository productRepository, SellerRepository sellerRepository) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return ProductMapper.toDTOs(productRepository.findAll());
    }

    @Override
    public List<ProductDTO> getAllProductsBySellerId(Integer sellerId) {
        sellerRepository.findById(sellerId)
            .orElseThrow(() -> new RuntimeException("sellerId not found"));
        return ProductMapper.toDTOs(productRepository.findByVendedor_Id(sellerId));
    }
}
