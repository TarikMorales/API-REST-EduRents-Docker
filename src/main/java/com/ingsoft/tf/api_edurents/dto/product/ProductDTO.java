package com.ingsoft.tf.api_edurents.dto.product;

import com.ingsoft.tf.api_edurents.model.entity.product.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {

    private Integer id_vendedor;
    private String nombre;
    private String descripcion;
    private Double precio;

    @Enumerated(EnumType.STRING)
    private ProductStatus estado;

    private Integer cantidad_disponible;
    private Boolean acepta_intercambio;

    private List<String> urls_imagenes;
    private List<Integer> categorias;
    private List<Integer> cursos_carreras;
}
