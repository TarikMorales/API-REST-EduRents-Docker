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

    // HU09

    // ENDPOIINT CAMBIO CONTRASEÑA

    @Test
    @DisplayName("HU9 - CP01 - Cambio de contraseña exitoso")
    void cambioContrasena_datosValidos_devuelveDTO() {
        RecoverPasswordDTO dto = new RecoverPasswordDTO();
        dto.setCorreo("test@mail.com");
        dto.setContrasena("oldPass");
        dto.setNuevaContrasena("newPass");

        User user = new User();
        user.setId(1);
        user.setCorreo("test@mail.com");
        user.setContrasena("oldPass");

        User updated = new User();
        updated.setContrasena("newPass");

        UserDTO response = new UserDTO();
        response.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updated);
        when(userMapper.toResponse(any(User.class))).thenReturn(response);

        UserDTO result = userAuthUserService.cambioContrasenaUsuario(1, dto);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    @DisplayName("HU9 - CP02 - Cambio de contraseña con correo incorrecto")
    void cambioContrasena_correoIncorrecto_lanzaExcepcion() {
        RecoverPasswordDTO dto = new RecoverPasswordDTO();
        dto.setCorreo("otro@mail.com");
        dto.setContrasena("oldPass");
        dto.setNuevaContrasena("newPass");

        User user = new User(); user.setId(1);
        user.setCorreo("test@mail.com");
        user.setContrasena("oldPass");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () ->
                userAuthUserService.cambioContrasenaUsuario(1, dto));
    }






}
