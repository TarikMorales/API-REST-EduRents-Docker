package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.ProductDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {
    public static ProductDTO toDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setNombre(product.getNombre());
        productDTO.setDescripcion(product.getDescripcion());
        productDTO.setPrecio(product.getPrecio());
        productDTO.setVendedor(product.getVendedor());
        return productDTO;
    }
    public static List<ProductDTO> toDTOs(List<Product> products){
        return products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }
}
