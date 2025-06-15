package com.ingsoft.tf.api_edurents.service.Public;

import com.ingsoft.tf.api_edurents.config.TokenProvider;
import com.ingsoft.tf.api_edurents.config.UserPrincipal;
import com.ingsoft.tf.api_edurents.dto.auth.RecoverProcessDTO;
import com.ingsoft.tf.api_edurents.dto.user.AuthResponseDTO;
import com.ingsoft.tf.api_edurents.dto.user.LoginDTO;
import com.ingsoft.tf.api_edurents.dto.user.RegisterDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.mapper.RecoverProcessMapper;
import com.ingsoft.tf.api_edurents.mapper.UserMapper;
import com.ingsoft.tf.api_edurents.model.entity.auth.RecoverProcess;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.auth.RecoverProcessRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.impl.Public.PublicUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PublicUserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RecoverProcessMapper recoverProcessMapper;

    @Mock
    private RecoverProcessRepository recoverProcessRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    //@InjectMocks
    //private PublicUserServiceImpl publicUserService;

    private PublicUserServiceImpl publicUserService;

    //@BeforeEach
    //void setUp() {
    //    MockitoAnnotations.openMocks(this);
    //}

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Instancia el servicio con las dependencias que están por constructor
        publicUserService = new PublicUserServiceImpl(authenticationManager, tokenProvider);

        // Inyecta manualmente los mocks en los campos con @Autowired
        ReflectionTestUtils.setField(publicUserService, "userRepository", userRepository);
        ReflectionTestUtils.setField(publicUserService, "recoverProcessRepository", recoverProcessRepository);
        ReflectionTestUtils.setField(publicUserService, "recoverProcessMapper", recoverProcessMapper);
        ReflectionTestUtils.setField(publicUserService, "userMapper", userMapper);
        ReflectionTestUtils.setField(publicUserService, "passwordEncoder", passwordEncoder);
    }

    // HU 07

    // Endpoint Register

    @Test
    @DisplayName("HU7 - CP01 - Registrar usuario con datos válidos")
    void registerUsuario_datosValidos_devuelveDTO() {
        RegisterDTO dto = new RegisterDTO();
        dto.setCorreo("test@mail.com");

        User entity = new User();
        UserDTO response = new UserDTO();

        when(userRepository.existsUserByCorreo("test@mail.com")).thenReturn(false);
        when(userMapper.toEntity(dto)).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(entity);
        when(userMapper.toResponse(entity)).thenReturn(response);

        UserDTO result = publicUserService.registerUsuario(dto);

        assertNotNull(result);
        verify(userRepository).save(entity);
    }

    @Test
    @DisplayName("HU7 - CP02 - Registro con correo ya registrado lanza excepción")
    void registerUsuario_correoYaRegistrado_lanzaExcepcion() {
        RegisterDTO dto = new RegisterDTO();
        dto.setCorreo("test@mail.com");

        when(userRepository.existsUserByCorreo("test@mail.com")).thenReturn(true);

        assertThrows(BadRequestException.class, () -> publicUserService.registerUsuario(dto));
    }

    // Endpoint Login

    @Test
    @DisplayName("HU7 - CP03 - Login exitoso")
    void loginUsuario_datosCorrectos_devuelveDTO() {

        LoginDTO dto = new LoginDTO();
        dto.setCorreo("test@mail.com");
        dto.setContrasena("pass123");

        User user = new User();
        user.setCorreo("test@mail.com");

        UserPrincipal userPrincipal = mock(UserPrincipal.class);
        Authentication authentication = mock(Authentication.class);

        String token = "fake-jwt-token";
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setToken(token);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userPrincipal);
        when(userPrincipal.getUser()).thenReturn(user);
        when(tokenProvider.createAccessToken(authentication)).thenReturn(token);
        when(userMapper.toAuthResponse(user, token)).thenReturn(authResponseDTO);

        AuthResponseDTO result = publicUserService.loginUsuario(dto);

        assertNotNull(result);
        assertEquals(token, result.getToken());

    }

    @Test
    @DisplayName("HU7 - CP04 - Login con contraseña incorrecta lanza excepción")
    void loginUsuario_contrasenaIncorrecta_lanzaExcepcion() {
        LoginDTO dto = new LoginDTO();
        dto.setCorreo("test@mail.com");
        dto.setContrasena("contraseñaIncorrecta");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Credenciales inválidas"));

        assertThrows(BadCredentialsException.class, () -> publicUserService.loginUsuario(dto));

    }

}
