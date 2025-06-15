package com.ingsoft.tf.api_edurents.service.auth.user;

import com.ingsoft.tf.api_edurents.dto.user.RecoverPasswordDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.UserMapper;
import com.ingsoft.tf.api_edurents.model.entity.university.Career;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.university.CareerRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.impl.Public.PublicUserServiceImpl;
import com.ingsoft.tf.api_edurents.service.impl.auth.user.UserAuthUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserAuthUserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    CareerRepository careerRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserAuthUserServiceImpl userAuthUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("HU7 - CP05 - Obtener usuario por ID válido")
    void obtenerUsuarioPorId_existente_devuelveDTO() {
        User user = new User(); user.setId(1);
        UserDTO dto = new UserDTO(); dto.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(dto);

        UserDTO result = userAuthUserService.obtenerUsuarioPorId(1);
        assertEquals(1, result.getId());
    }

    @Test
    @DisplayName("HU7 - CP06 - Obtener usuario inexistente lanza excepción")
    void obtenerUsuarioPorId_noExiste_lanzaExcepcion() {
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userAuthUserService.obtenerUsuarioPorId(99));
    }

}
