package com.ingsoft.tf.api_edurents.repository.product;

import com.ingsoft.tf.api_edurents.model.entity.product.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Image i WHERE i.producto.id = :productId")
    void deleteImagesByProductID(@Param("productId") int productId);

}
