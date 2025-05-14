package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.ProductDTO;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.university.Career;
import com.ingsoft.tf.api_edurents.model.entity.university.Course;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.repository.university.CareerRepository;
import com.ingsoft.tf.api_edurents.repository.university.CourseRepository;
import com.ingsoft.tf.api_edurents.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final CareerRepository careerRepository;

    @Autowired
    private final CourseRepository courseRepository;

    public ProductServiceImpl(ProductRepository productRepository, CareerRepository careerRepository, CourseRepository courseRepository) {
        this.productRepository = productRepository;
        this.careerRepository = careerRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<ProductDTO> getFilteredProducts(Integer carreraId, Integer cursoID) {
        // Verificar si el curso existe
        Optional<Course> courseOptional = courseRepository.findById(cursoID);
        if (!courseOptional.isPresent()) {
            throw new RuntimeException("Curso no encontrado con ID: " + cursoID);
        }

        // Verificar si la carrera existe
        Optional<Career> careerOptional = careerRepository.findById(carreraId);
        if (!careerOptional.isPresent()) {
            throw new RuntimeException("Carrera no encontrada con ID: " + carreraId);
        }

        // Si ambos existen, buscar los productos
        List<Product> products = productRepository.findByCareerAndCourse(carreraId, cursoID);

        if (products.isEmpty()) {
            throw new RuntimeException("No se encontraron productos para la Carrera ID: " + carreraId + " y Curso ID: " + cursoID);
        }

        return ProductMapper.toDTOs(products);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return ProductMapper.toDTOs(productRepository.findAll());
    }
}
