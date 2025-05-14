package com.ingsoft.tf.api_edurents.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Maneja RuntimeException (como el curso no encontrado o carrera no encontrada)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        String errorMessage = ex.getMessage();  // Obtiene el mensaje de la excepción
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND); // 404 - No encontrado
    }
}
