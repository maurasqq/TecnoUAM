package com.tecnouam.backend.controller;

import com.tecnouam.backend.dto.PrestamoRequestDTO;
import com.tecnouam.backend.dto.PrestamoResponseDTO;
import com.tecnouam.backend.service.PrestamoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @GetMapping
    public ResponseEntity<List<PrestamoResponseDTO>> listar() {
        return ResponseEntity.ok(prestamoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestamoResponseDTO> buscarPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(prestamoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PrestamoResponseDTO> crear(
            @Valid @RequestBody PrestamoRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(prestamoService.crear(request));
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<PrestamoResponseDTO> devolver(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(prestamoService.devolver(id));
    }
}