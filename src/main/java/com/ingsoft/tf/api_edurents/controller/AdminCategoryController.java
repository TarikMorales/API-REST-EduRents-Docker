package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.product.CategoryDTO;
import com.ingsoft.tf.api_edurents.service.AdminCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class AdminCategoryController {
    private final AdminCategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();
        return new ResponseEntity<List<CategoryDTO>>(categoryDTOList, HttpStatus.OK);
    }
}
