package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.product.CategoryDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.model.entity.product.Category;
import com.ingsoft.tf.api_edurents.repository.product.CategoryRepository;
import com.ingsoft.tf.api_edurents.service.AdminCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;

    public CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setNombre(category.getNombre());

        return categoryDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("No se han encontrado categorías disponibles");
        }
        return categories.stream()
                .map(this::convertToDTO)
                .toList();
    };
}
