package com.tecnouam.backend.service;

import com.tecnouam.backend.dto.EquipoRequestDTO;
import com.tecnouam.backend.dto.EquipoResponseDTO;

import java.util.List;

public interface EquipoService {

    List<EquipoResponseDTO> listar();

    EquipoResponseDTO buscarPorId(Long id);

    EquipoResponseDTO crear(EquipoRequestDTO request);

    EquipoResponseDTO actualizar(Long id, EquipoRequestDTO request);

    void eliminar(Long id);
}