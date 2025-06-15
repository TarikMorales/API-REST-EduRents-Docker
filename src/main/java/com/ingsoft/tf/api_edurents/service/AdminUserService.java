package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.user.RecoverPasswordDTO;
import com.ingsoft.tf.api_edurents.dto.user.LoginDTO;
import com.ingsoft.tf.api_edurents.dto.user.RegisterDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;

public interface AdminUserService {

    UserDTO obtenerUsuarioPorId(Integer id);

    UserDTO cambioContrasenaUsuario(Integer id, RecoverPasswordDTO nuevosDatos);

    UserDTO actualizarDatosUsuario(Integer id, UserDTO usuarioDTO);

    UserDTO cambiarFotoUsuario(Integer id, String urlFoto);

    UserDTO cambiarCarreraUsuario(Integer id, Integer idCarrera);

    UserDTO cambiarCicloUsuario(Integer id, Byte ciclo);

}
