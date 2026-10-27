
package com.tecnouam.backend.service.impl;

import com.tecnouam.backend.dto.EquipoRequestDTO;
import com.tecnouam.backend.dto.EquipoResponseDTO;
import com.tecnouam.backend.entity.Equipo;
import com.tecnouam.backend.entity.EstadoEquipo;
import com.tecnouam.backend.entity.EstadoPrestamo;
import com.tecnouam.backend.repository.EquipoRepository;
import com.tecnouam.backend.repository.PrestamoRepository;
import com.tecnouam.backend.service.EquipoService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EquipoServiceImpl implements EquipoService {

    private final EquipoRepository equipoRepository;
    private final PrestamoRepository prestamoRepository;

    public EquipoServiceImpl(
            EquipoRepository equipoRepository,
            PrestamoRepository prestamoRepository
    ) {
        this.equipoRepository = equipoRepository;
        this.prestamoRepository = prestamoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipoResponseDTO> listar() {
        return equipoRepository.findByEliminadoFalse()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EquipoResponseDTO buscarPorId(Long id) {
        Equipo equipo = obtenerEquipo(id);
        return convertirDTO(equipo);
    }

    @Override
    @Transactional
    public EquipoResponseDTO crear(EquipoRequestDTO request) {

        if (equipoRepository.existsByCodigoPatrimonial(
                request.getCodigoPatrimonial())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un equipo con ese código patrimonial"
            );
        }

        Equipo equipo = new Equipo();
        equipo.setCodigoPatrimonial(request.getCodigoPatrimonial());
        equipo.setNombre(request.getNombre());
        equipo.setTipo(request.getTipo());
        equipo.setEstado(EstadoEquipo.DISPONIBLE);

        return convertirDTO(equipoRepository.save(equipo));
    }

    @Override
    @Transactional
    public EquipoResponseDTO actualizar(
            Long id,
            EquipoRequestDTO request
    ) {
        Equipo equipo = obtenerEquipo(id);

        String nuevoCodigo = request.getCodigoPatrimonial();

        if (!equipo.getCodigoPatrimonial().equals(nuevoCodigo)
                && equipoRepository.existsByCodigoPatrimonial(nuevoCodigo)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe otro equipo con ese código patrimonial"
            );
        }

        equipo.setCodigoPatrimonial(nuevoCodigo);
        equipo.setNombre(request.getNombre());
        equipo.setTipo(request.getTipo());

        return convertirDTO(equipoRepository.save(equipo));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Equipo equipo = obtenerEquipo(id);

        boolean tienePrestamoActivo =
                prestamoRepository.existsByEquipoIdAndEstado(
                        id,
                        EstadoPrestamo.ACTIVO
                );

        if (tienePrestamoActivo) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar un equipo con un préstamo activo"
            );
        }

        equipo.setEliminado(true);
        equipoRepository.save(equipo);
    }

    private Equipo obtenerEquipo(Long id) {
        return equipoRepository.findByIdAndEliminadoFalse(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Equipo no encontrado"
                ));
    }

    private EquipoResponseDTO convertirDTO(Equipo equipo) {
        return new EquipoResponseDTO(
                equipo.getId(),
                equipo.getCodigoPatrimonial(),
                equipo.getNombre(),
                equipo.getTipo(),
                equipo.getEstado()
        );
    }
}
