package com.ingsoft.tf.api_edurents.repository.product;

import com.ingsoft.tf.api_edurents.model.entity.product.CategoriesProducts;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p JOIN p.categorias cp WHERE cp.categoria.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Integer categoryId);
    //Puedes omitir el Param, spring lo va a mapear si setá con el mismo nombre
}
