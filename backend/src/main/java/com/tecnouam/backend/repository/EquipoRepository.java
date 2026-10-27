package com.tecnouam.backend.repository;

import com.tecnouam.backend.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    boolean existsByCodigoPatrimonial(String codigoPatrimonial);

    List<Equipo> findByEliminadoFalse();

    Optional<Equipo> findByIdAndEliminadoFalse(Long id);
}