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



        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updated);
        when(userMapper.toResponse(any(User.class))).thenReturn(response);

        UserDTO result = userAuthUserService.cambiarFotoUsuario(1, "https://imagen.com/perfil.jpg");

        assertNotNull(result);
        assertEquals("https://imagen.com/perfil.jpg", result.getFoto_url());
    }


    @Test
    @DisplayName("HU8 - CP04 - Cambiar foto con URL vacía lanza excepción")
    void cambiarFotoUsuario_urlInvalida_lanzaExcepcion() {
        User user = new User(); user.setId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () ->
                userAuthUserService.cambiarFotoUsuario(1, ""));
    }


    // NEDPO9INT cambair carrera

    @Test
    @DisplayName("HU8 - CP05 - Cambiar carrera con datos válidos")
    void cambiarCarreraUsuario_valido_devuelveDTO() {
        User user = new User(); user.setId(1);
        Career carrera = new Career(); carrera.setId(2);
        User updated = new User(); updated.setCarrera(carrera);

        UserDTO response = new UserDTO();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(careerRepository.findById(2)).thenReturn(Optional.of(carrera));
        when(userRepository.save(any(User.class))).thenReturn(updated);
        when(userMapper.toResponse(any(User.class))).thenReturn(response);

        UserDTO result = userAuthUserService.cambiarCarreraUsuario(1, 2);
        assertNotNull(result);
    }


    @Test
    @DisplayName("HU8 - CP06 - Cambiar carrera con carrera inexistente")
    void cambiarCarreraUsuario_idCarreraInvalido_lanzaExcepcion() {
        User user = new User(); user.setId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(careerRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                userAuthUserService.cambiarCarreraUsuario(1, 99));
    }
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
