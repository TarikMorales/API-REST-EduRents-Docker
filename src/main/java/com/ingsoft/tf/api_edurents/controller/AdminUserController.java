package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.user.RegisterDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registro(@Valid @RequestBody RegisterDTO registro) {
        UserDTO userDTO = adminUserService.registerUsuario(registro);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);
    }

}
