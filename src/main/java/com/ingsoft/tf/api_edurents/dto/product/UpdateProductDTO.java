package com.ingsoft.tf.api_edurents.dto.product;

import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class UpdateProductDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Double precio;

    @Enumerated(EnumType.STRING)
    private ProductStatus estado;

    private Integer cantidad_disponible;
    private Boolean acepta_intercambio;

    private LocalDate fecha_creacion;
    private LocalDate fecha_modificacion;

    private SellerDTO vendedor;

    private List<ImageDTO> imagenes;

    private List<CategoryDTO> categorias;

    private List<CourseCareerDTO> cursos_carreras;

}
