package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.user.RecoverPasswordDTO;
import com.ingsoft.tf.api_edurents.dto.user.LoginDTO;
import com.ingsoft.tf.api_edurents.dto.user.RegisterDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
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
    private CareerRepository careerRepository;

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

    private User convertToUser(RegisterDTO datosRegistro) {
        User usuario = new User();
        usuario.setNombres(datosRegistro.getNombres());
        usuario.setApellidos(datosRegistro.getApellidos());
        usuario.setCorreo(datosRegistro.getCorreo());
        usuario.setCodigo_universitario(datosRegistro.getCodigo_universitario());
        usuario.setCiclo(datosRegistro.getCiclo());
        Career carrera = careerRepository.findById(datosRegistro.getId_carrera())
                .orElseThrow(() -> new ResourceNotFoundException("La carrera no existe"));
        usuario.setCarrera(carrera);
        usuario.setContrasena(datosRegistro.getContrasena());
        usuario.setFoto_url(datosRegistro.getFoto_url());
        return usuario;
    }

    @Transactional
    @Override
    public UserDTO registerUsuario(RegisterDTO datosRegistro) {
        if (!userRepository.existsUserByCorreo(datosRegistro.getCorreo())) {
            User usuario = convertToUser(datosRegistro);
            userRepository.save(usuario);
            return convertToUserDTO(usuario);
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
                return convertToUserDTO(usuario);
            } else {
                throw new RuntimeException("La contraseña es incorrecta");
            }
        } else {
            throw new RuntimeException("Credenciales inválidas");
        }
      
    @Transactional
    @Override
    public UserDTO cambioContrasenaUsuario(Integer id, RecoverPasswordDTO nuevosDatos) {
        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));
        if (!usuario.getCorreo().equals(nuevosDatos.getCorreo())) {
            throw new RuntimeException("Correo incorrecto");
        }
        if (!usuario.getContrasena().equals(nuevosDatos.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        if (usuario.getContrasena().equals(nuevosDatos.getNuevaContrasena())) {
            throw new RuntimeException("La nueva contraseña no puede ser igual a la anterior");
        }

        usuario.setContrasena(nuevosDatos.getNuevaContrasena());
        userRepository.save(usuario);
        return convertToUserDTO(usuario);
    }
}
