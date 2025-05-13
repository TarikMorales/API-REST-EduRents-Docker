package com.ingsoft.tf.api_edurents.repository.product;

import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByVendedor_Id(Integer vendedorId);
}
