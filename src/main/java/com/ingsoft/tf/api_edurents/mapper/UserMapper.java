package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toResponse(User user) {
        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setNombres(user.getNombres());
        dto.setApellidos(user.getApellidos());
        dto.setCorreo(user.getCorreo());
        dto.setCodigo_universitario(user.getCodigo_universitario());
        dto.setCiclo(user.getCiclo());

        if (user.getCarrera() != null) {
            dto.setCarrera(user.getCarrera().getNombre());
        } else {
            dto.setCarrera(null);
        }

        return dto;
    }

}
