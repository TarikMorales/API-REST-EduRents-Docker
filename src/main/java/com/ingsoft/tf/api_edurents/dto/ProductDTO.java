package com.ingsoft.tf.api_edurents.dto;

import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import lombok.Data;

@Data
public class ProductDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Seller vendedor;
}
