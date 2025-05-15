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

    @Query("""
SELECT DISTINCT p FROM Product p
JOIN p.cursos_carreras ccp
WHERE ccp.curso_carrera.curso.id = :courseId
  AND ccp.curso_carrera.carrera.id = :careerId
""")
    List<Product> findByCareerAndCourse(
            @Param("careerId") Integer careerId,
            @Param("courseId") Integer courseId
    );

    @Query("SELECT p FROM Product p WHERE p.vendedor.id = :id_vendedor")
    List<Product> findByVendedor(@Param("id_vendedor") Integer id_vendedor);
           
}
