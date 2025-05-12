package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.user.LoginDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginDTO datosLogin) {
        UserDTO userDTO = adminUserService.loginUsuario(datosLogin);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

}
