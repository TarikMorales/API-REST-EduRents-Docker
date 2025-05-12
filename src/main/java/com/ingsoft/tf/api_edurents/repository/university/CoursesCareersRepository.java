package com.ingsoft.tf.api_edurents.repository.university;

import com.ingsoft.tf.api_edurents.model.entity.university.CoursesCareers;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CoursesCareersRepository extends JpaRepository<CoursesCareers, Integer> {

    @Query("SELECT cc FROM CoursesCareers cc WHERE cc.carrera.id = :idCarrera")
    List<CoursesCareers> findByCareerId(@Param("idCarrera") Integer idCarrera);

}
