package com.ingsoft.tf.api_edurents.repository.product;

import com.ingsoft.tf.api_edurents.model.entity.product.CoursesCareersProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CoursesCareersProductRepository extends JpaRepository<CoursesCareersProduct, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM CoursesCareersProduct ccp WHERE ccp.producto.id = :productId")
    void deleteCCPByProductID(@Param("productId") int productId);
}
