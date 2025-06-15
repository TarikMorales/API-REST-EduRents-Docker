package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.user.RecoverPasswordDTO;
import com.ingsoft.tf.api_edurents.dto.user.LoginDTO;
import com.ingsoft.tf.api_edurents.dto.user.RegisterDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.UserMapper;
import com.ingsoft.tf.api_edurents.model.entity.university.Career;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.university.CareerRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
      
    @Transactional
    @Override
    public UserDTO cambioContrasenaUsuario(Integer id, RecoverPasswordDTO nuevosDatos) {
        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe"));
        if (!usuario.getCorreo().equals(nuevosDatos.getCorreo())) {
            throw new BadRequestException("Correo incorrecto");
        }
        if (!usuario.getContrasena().equals(nuevosDatos.getContrasena())) {
            throw new BadRequestException("Contraseña incorrecta");
        }
        if (usuario.getContrasena().equals(nuevosDatos.getNuevaContrasena())) {
            throw new BadRequestException("La nueva contraseña no puede ser igual a la anterior");
        }

        usuario.setContrasena(nuevosDatos.getNuevaContrasena());
        userRepository.save(usuario);
        return userMapper.toResponse(usuario);
    }
}
