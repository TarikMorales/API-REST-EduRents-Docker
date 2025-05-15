package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.university.CourseDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.model.entity.university.CoursesCareers;
import com.ingsoft.tf.api_edurents.repository.university.CourseRepository;
import com.ingsoft.tf.api_edurents.repository.university.CoursesCareersRepository;
import com.ingsoft.tf.api_edurents.service.AdminCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCourseServiceImpl implements AdminCourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CoursesCareersRepository coursesCareersRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CourseDTO> obtenerCursosPorCarrera(Integer idCarrera) {
        List<CoursesCareers> cursosCarreras = coursesCareersRepository.findByCareerId(idCarrera);
        if (!cursosCarreras.isEmpty()) {
            return cursosCarreras.stream()
                    .map(cursoCarrera -> {
                        CourseDTO courseDTO = new CourseDTO();
                        courseDTO.setId(cursoCarrera.getCurso().getId());
                        courseDTO.setNombre(cursoCarrera.getCurso().getNombre());
                        courseDTO.setCodigo(cursoCarrera.getCurso().getCodigo());
                        return courseDTO;
                    })
                    .toList();
        } else {
            throw new ResourceNotFoundException("No se encontraron cursos para la carrera con ID: " + idCarrera);
        }
    }

}
