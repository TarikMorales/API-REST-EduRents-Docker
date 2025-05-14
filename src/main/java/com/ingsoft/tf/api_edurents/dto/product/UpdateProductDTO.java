package com.ingsoft.tf.api_edurents.dto.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class UpdateProductDTO {

    @NotNull(message = "El ID del producto no puede ser nulo")
    private Integer id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 1000, message = "La descripción no puede tener más de 1000 caracteres")
    private String descripcion;

    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private Double precio;

    @NotNull(message = "El estado del producto no puede ser nulo")
    @Enumerated(EnumType.STRING)
    private ProductStatus estado;

    @NotNull(message = "La cantidad disponible no puede ser nula")
    @Min(value = 0, message = "La cantidad disponible no puede ser negativa")
    private Integer cantidad_disponible;

    @NotNull(message = "Debe indicarse si se acepta intercambio")
    private Boolean acepta_intercambio;

    @PastOrPresent(message = "La fecha de creación no puede ser futura")
    private LocalDate fecha_creacion;

    @PastOrPresent(message = "La fecha de modificación no puede ser futura")
    private LocalDate fecha_modificacion;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_expiracion;

    @NotNull(message = "El vendedor no puede ser nulo")
    private SellerDTO vendedor;

    @NotNull(message = "Las imágenes no pueden ser nulas")
    @Size(min = 1, message = "Debe haber al menos una imagen")
    private List<@Valid ImageDTO> imagenes;

    @NotNull(message = "Las categorías no pueden ser nulas")
    @Size(min = 1, message = "Debe haber al menos una categoría")
    private List<@Valid CategoryDTO> categorias;

    @NotNull(message = "Los cursos/carreras no pueden ser nulos")
    private List<@Valid CourseCareerDTO> cursos_carreras;
}
