package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.user.LoginDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
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

    public UserDTO convertToUserDTO(User usuario) {
        UserDTO usuarioDTO = new UserDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombres(usuario.getNombres());
        usuarioDTO.setApellidos(usuario.getApellidos());
        usuarioDTO.setCorreo(usuario.getCorreo());
        usuarioDTO.setCodigo_universitario(usuario.getCodigo_universitario());
        usuarioDTO.setCiclo(usuario.getCiclo());
        usuarioDTO.setCarrera(usuario.getCarrera().getNombre());
        return usuarioDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO loginUsuario(LoginDTO datosLogin) {
        if (userRepository.existsUserByCorreo(datosLogin.getCorreo())){
            User usuario = userRepository.findByCorreoAndContrasena(datosLogin.getCorreo(), datosLogin.getContrasena());
            if (usuario != null) {
                return convertToUserDTO(usuario);
            } else {
                throw new RuntimeException("La contraseña es incorrecta");
            }
        } else {
            throw new RuntimeException("Credenciales inválidas");
        }
    }
}
