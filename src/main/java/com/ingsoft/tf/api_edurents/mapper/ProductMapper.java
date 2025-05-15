package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.ProductDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.CategoriesProducts;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {
  
    public static ProductDTO toDTO(Product product){
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setNombre(product.getNombre());
        dto.setPrecio(product.getPrecio());
        if (product.getCategorias() != null && !product.getCategorias().isEmpty()) {
            List<CategoriesProducts> categorias = product.getCategorias();
            dto.setCategorias(categorias);
        }
        return dto;
    }
    public static List<ProductDTO> toDTOs(List<Product> products){
        return products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }
}
