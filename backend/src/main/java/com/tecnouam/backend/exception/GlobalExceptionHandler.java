package com.tecnouam.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Capturar errores de validación de los DTOs (@NotBlank, @NotNull, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidacion(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errores = new LinkedHashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("timestamp", LocalDateTime.now());
        respuesta.put("status", 400);
        respuesta.put("error", "Bad Request");
        respuesta.put("message", "Error de validación");
        respuesta.put("details", errores);

        return ResponseEntity.badRequest().body(respuesta);
    }

    // Capturar errores de negocio: 404, 409, etc.
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> manejarEstado(
            ResponseStatusException ex
    ) {
        int status = ex.getStatusCode().value();

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("timestamp", LocalDateTime.now());
        respuesta.put("status", status);
        respuesta.put("error", ex.getStatusCode().toString());
        respuesta.put("message", ex.getReason());

        return ResponseEntity.status(ex.getStatusCode()).body(respuesta);
    }

    // Capturar JSON mal formado o valores incompatibles
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> manejarJson(
            HttpMessageNotReadableException ex
    ) {
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("timestamp", LocalDateTime.now());
        respuesta.put("status", 400);
        respuesta.put("error", "Bad Request");
        respuesta.put("message", "El cuerpo de la solicitud JSON no es válido");

        return ResponseEntity.badRequest().body(respuesta);
    }

    // Capturar errores inesperados del servidor
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarGeneral(
            Exception ex
    ) {
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("timestamp", LocalDateTime.now());
        respuesta.put("status", 500);
        respuesta.put("error", "Internal Server Error");
        respuesta.put("message", "Ocurrió un error interno del servidor");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(respuesta);
    }
}