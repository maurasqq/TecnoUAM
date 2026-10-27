
package com.tecnouam.backend.service.impl;

import com.tecnouam.backend.dto.PrestamoRequestDTO;
import com.tecnouam.backend.dto.PrestamoResponseDTO;
import com.tecnouam.backend.entity.Equipo;
import com.tecnouam.backend.entity.EstadoEquipo;
import com.tecnouam.backend.entity.EstadoPrestamo;
import com.tecnouam.backend.entity.Prestamo;
import com.tecnouam.backend.repository.EquipoRepository;
import com.tecnouam.backend.repository.PrestamoRepository;
import com.tecnouam.backend.service.PrestamoService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final EquipoRepository equipoRepository;

    public PrestamoServiceImpl(
            PrestamoRepository prestamoRepository,
            EquipoRepository equipoRepository
    ) {
        this.prestamoRepository = prestamoRepository;
        this.equipoRepository = equipoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrestamoResponseDTO> listar() {
        return prestamoRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PrestamoResponseDTO buscarPorId(Long id) {
        Prestamo prestamo = obtenerPrestamo(id);
        return convertirDTO(prestamo);
    }

    @Override
    @Transactional
    public PrestamoResponseDTO crear(PrestamoRequestDTO request) {

        // 1. Comprobar que el equipo existe
        Equipo equipo = equipoRepository
                .findByIdAndEliminadoFalse(request.getEquipoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "El equipo solicitado no existe"
                ));

        // 2. Verificar que esté disponible
        if (equipo.getEstado() != EstadoEquipo.DISPONIBLE) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El equipo ya está prestado"
            );
        }

        // 3. Comprobar que no tenga préstamos activos
        boolean prestamoActivo =
                prestamoRepository.existsByEquipoIdAndEstado(
                        equipo.getId(),
                        EstadoPrestamo.ACTIVO
                );

        if (prestamoActivo) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El equipo ya tiene un préstamo activo"
            );
        }

        // 4. Validar las fechas
        if (request.getFechaPrestamo() == null
                || request.getFechaPrevistaDevolucion() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Las fechas del préstamo son obligatorias"
            );
        }

        if (request.getFechaPrevistaDevolucion()
                .isBefore(request.getFechaPrestamo())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La fecha de devolución no puede ser anterior al préstamo"
            );
        }

        // 5. Crear el préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setEquipo(equipo);
        prestamo.setSolicitante(request.getSolicitante());
        prestamo.setFechaPrestamo(request.getFechaPrestamo());
        prestamo.setFechaPrevistaDevolucion(
                request.getFechaPrevistaDevolucion()
        );
        prestamo.setEstado(EstadoPrestamo.ACTIVO);

        // 6. Cambiar el estado del equipo
        equipo.setEstado(EstadoEquipo.PRESTADO);

        // 7. Guardar ambos cambios en una transacción
        equipoRepository.save(equipo);
        Prestamo guardado = prestamoRepository.save(prestamo);

        return convertirDTO(guardado);
    }

    @Override
    @Transactional
    public PrestamoResponseDTO devolver(Long id) {

        // 1. Buscar el préstamo
        Prestamo prestamo = obtenerPrestamo(id);

        // 2. Evitar devolver el mismo préstamo dos veces
        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este préstamo ya fue devuelto"
            );
        }

        // 3. Cambiar el préstamo a DEVUELTO
        prestamo.setEstado(EstadoPrestamo.DEVUELTO);

        // 4. Liberar el equipo
        Equipo equipo = prestamo.getEquipo();
        equipo.setEstado(EstadoEquipo.DISPONIBLE);

        // 5. Guardar ambos cambios
        equipoRepository.save(equipo);
        Prestamo actualizado = prestamoRepository.save(prestamo);

        return convertirDTO(actualizado);
    }

    private Prestamo obtenerPrestamo(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Préstamo no encontrado"
                ));
    }

    private PrestamoResponseDTO convertirDTO(Prestamo prestamo) {
        return new PrestamoResponseDTO(
                prestamo.getId(),
                prestamo.getEquipo().getId(),
                prestamo.getSolicitante(),
                prestamo.getFechaPrestamo(),
                prestamo.getFechaPrevistaDevolucion(),
                prestamo.getEstado()
        );
    }
}
