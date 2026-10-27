package com.tecnouam.backend.repository;

import com.tecnouam.backend.entity.Prestamo;
import com.tecnouam.backend.entity.EstadoPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    boolean existsByEquipoIdAndEstado(Long equipoId, EstadoPrestamo estado);
}