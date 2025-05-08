package com.ingsoft.tf.api_edurents.repository.product;

import com.ingsoft.tf.api_edurents.model.entity.product.CategoriesProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CategoriesProductsRepository extends JpaRepository<CategoriesProducts, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM CategoriesProducts cp WHERE cp.producto.id = :productId")
    void deleteCatProdByProductID(@Param("productId") int productId);
}
