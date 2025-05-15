package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.ProductDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {
    public static ProductDTO toDTO(Product product){
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setNombre(product.getNombre());
        dto.setPrecio(product.getPrecio());
        dto.setEstado(product.getEstado());

        // Extraer el primer id de la lista, si está presente
        if (!product.getCategorias().isEmpty()) {
            dto.setIdCategoria(product.getCategorias().get(0).getCategoria().getId());
        }

        if (!product.getCursos_carreras().isEmpty()) {
            var ccp = product.getCursos_carreras().get(0); // CoursesCareersProduct
            var cc = ccp.getCurso_carrera(); // CoursesCareers
            if (cc != null) {
                if (cc.getCurso() != null) {
                    dto.setIdCurso(cc.getCurso().getId());
                }
                if (cc.getCarrera() != null) {
                    dto.setIdCarrera(cc.getCarrera().getId());
                }
            }
        }
        return dto;
    }
    public static List<ProductDTO> toDTOs(List<Product> product){
        return product.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }
}
