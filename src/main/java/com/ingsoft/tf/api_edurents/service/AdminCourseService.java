package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.university.CourseDTO;

import java.util.List;

public interface AdminCourseService {

    List<CourseDTO> obtenerCursosPorCarrera(Integer idCarrera);

}
