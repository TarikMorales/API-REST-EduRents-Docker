package com.ingsoft.tf.api_edurents.dto;

import com.ingsoft.tf.api_edurents.model.entity.product.CategoriesProducts;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private Integer id;
    private String nombre;
    private Double precio;
    private List<CategoriesProducts> categorias;
}
