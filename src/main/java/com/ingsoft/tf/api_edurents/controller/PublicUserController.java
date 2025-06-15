package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.user.LoginDTO;
import com.ingsoft.tf.api_edurents.dto.user.RegisterDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.service.PublicUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class PublicUserController {

    private final PublicUserService publicUserService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterDTO datosRegistro) {
        UserDTO userDTO = publicUserService.registerUsuario(datosRegistro);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginDTO datosLogin) {
        UserDTO userDTO = publicUserService.loginUsuario(datosLogin);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }
}