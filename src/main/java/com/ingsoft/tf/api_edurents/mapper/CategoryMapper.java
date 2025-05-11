package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.CategoryDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {
    public static CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        category.getId();
        category.getNombre();
        return dto;
    }
    public static List<CategoryDTO> toDTOs(List<Category> category){
        return category.stream().map(CategoryMapper::toDTO).collect(Collectors.toList());
    }
}
