package com.ingsoft.tf.api_edurents.dto;

import com.ingsoft.tf.api_edurents.model.entity.product.CategoriesProducts;
import java.util.List;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import com.ingsoft.tf.api_edurents.model.entity.product.ProductStatus;
import lombok.Data;

@Data
public class ProductDTO {
    private Integer id;
    private String nombre;
    private List<CategoriesProducts> categorias;
    private String descripcion;
    private Seller vendedor;
    private Double precio;
    private ProductStatus estado;
    private Integer idCategoria;
    private Integer idCurso;
    private Integer idCarrera;
}
