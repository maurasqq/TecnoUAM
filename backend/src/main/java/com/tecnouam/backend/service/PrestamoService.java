package com.tecnouam.backend.service;

import com.tecnouam.backend.dto.PrestamoRequestDTO;
import com.tecnouam.backend.dto.PrestamoResponseDTO;

import java.util.List;

public interface PrestamoService {

    List<PrestamoResponseDTO> listar();

    PrestamoResponseDTO buscarPorId(Long id);

    PrestamoResponseDTO crear(PrestamoRequestDTO request);

    PrestamoResponseDTO devolver(Long id);
}git add backend/src/main/java/com/tecnouam/backend/service/EquipoService.java