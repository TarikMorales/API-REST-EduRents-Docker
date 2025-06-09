package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.user.LoginDTO;
import com.ingsoft.tf.api_edurents.dto.user.RegisterDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.mapper.UserMapper;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.PublicUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PublicUserServiceImpl implements PublicUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public UserDTO registerUsuario(RegisterDTO datosRegistro) {
        if (!userRepository.existsUserByCorreo(datosRegistro.getCorreo())) {
            User usuario = userMapper.toEntity(datosRegistro);
            userRepository.save(usuario);
            return userMapper.toResponse(usuario);
        } else {
            throw new BadRequestException("El correo ya está registrado en otra cuenta");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO loginUsuario(LoginDTO datosLogin) {
        if (userRepository.existsUserByCorreo(datosLogin.getCorreo())){
            User usuario = userRepository.findByCorreoAndContrasena(datosLogin.getCorreo(), datosLogin.getContrasena());
            if (usuario != null) {
                return userMapper.toResponse(usuario);
            } else {
                throw new BadRequestException("La contraseña es incorrecta");
            }
        } else {
            throw new BadRequestException("Credenciales inválidas");
        }
    }
}
