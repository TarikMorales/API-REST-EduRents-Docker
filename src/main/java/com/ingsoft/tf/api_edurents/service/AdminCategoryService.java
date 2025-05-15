package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.product.CategoryDTO;

import java.util.List;

public interface AdminCategoryService {
    List<CategoryDTO> getAllCategories();
}
