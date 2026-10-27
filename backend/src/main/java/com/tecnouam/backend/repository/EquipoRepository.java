package com.tecnouam.backend.repository;

import com.tecnouam.backend.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    boolean existsByCodigoPatrimonial(String codigoPatrimonial);
}