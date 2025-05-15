package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.ProductDTO;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.CategoriesProducts;
import com.ingsoft.tf.api_edurents.model.entity.product.Category;
import com.ingsoft.tf.api_edurents.repository.product.CategoriesProductsRepository;
import com.ingsoft.tf.api_edurents.repository.product.CategoryRepository;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.university.Career;
import com.ingsoft.tf.api_edurents.model.entity.university.Course;
import com.ingsoft.tf.api_edurents.repository.university.CareerRepository;
import com.ingsoft.tf.api_edurents.repository.university.CourseRepository;
import com.ingsoft.tf.api_edurents.repository.user.SellerRepository;
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
    private final SellerRepository sellerRepository;
  
    @Autowired
    private final CategoryRepository categoryRepository;
  
    @Autowired
    private final CareerRepository careerRepository;

    @Autowired
    private final CourseRepository courseRepository;
  
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductServiceImpl(ProductRepository productRepository, CareerRepository careerRepository, CourseRepository courseRepository) {
        this.productRepository = productRepository;
        this.careerRepository = careerRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<ProductDTO> getFilteredProducts(Integer carreraId, Integer cursoId) {
        // Verificar si el curso existe
        Optional<Course> courseOptional = courseRepository.findById(cursoId);
        if (!courseOptional.isPresent()) {
            throw new RuntimeException("Curso no encontrado con ID: " + cursoId);
        }

        // Verificar si la carrera existe
        Optional<Career> careerOptional = careerRepository.findById(carreraId);
        if (!careerOptional.isPresent()) {
            throw new RuntimeException("Carrera no encontrada con ID: " + carreraId);
        }

        // Si ambos existen, buscar los productos
        List<Product> products = productRepository.findByCareerAndCourse(carreraId, cursoId);

        if (products.isEmpty()) {
            throw new RuntimeException("No se encontraron productos para la Carrera ID: " + carreraId + " y Curso ID: " + cursoId);
        }

        return ProductMapper.toDTOs(products);
    }

    public ProductServiceImpl(ProductRepository productRepository, SellerRepository sellerRepository) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public List<ProductDTO> findByCategoryId(Integer categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category ID not found: " + categoryId));

        List<Product> products = productRepository.findByCategoryId(categoryId);

        if (products.isEmpty()) {
            throw new RuntimeException("No products found for category ID: " + categoryId);
        }
        return ProductMapper.toDTOs(products);
    }
  
    @Override
    public List<ProductDTO> getAllProducts() {
        return ProductMapper.toDTOs(productRepository.findAll());
    }

    @Override
    public List<ProductDTO> getAllProductsBySellerId(Integer sellerId) {
        sellerRepository.findById(sellerId)
            .orElseThrow(() -> new RuntimeException("sellerId not found"));
        return ProductMapper.toDTOs(productRepository.findByVendedor_Id(sellerId));
    }
}
