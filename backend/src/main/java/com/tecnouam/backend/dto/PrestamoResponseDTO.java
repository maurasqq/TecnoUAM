package com.tecnouam.backend.dto;

import com.tecnouam.backend.entity.EstadoPrestamo;
import java.time.LocalDate;

public class PrestamoResponseDTO {

    private Long id;
    private Long equipoId;
    private String solicitante;
    private LocalDate fechaPrestamo;
    private LocalDate fechaPrevistaDevolucion;
    private EstadoPrestamo estado;

    public PrestamoResponseDTO(
            Long id,
            Long equipoId,
            String solicitante,
            LocalDate fechaPrestamo,
            LocalDate fechaPrevistaDevolucion,
            EstadoPrestamo estado
    ) {
        this.id = id;
        this.equipoId = equipoId;
        this.solicitante = solicitante;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaPrevistaDevolucion = fechaPrevistaDevolucion;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public Long getEquipoId() {
        return equipoId;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaPrevistaDevolucion() {
        return fechaPrevistaDevolucion;
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }
}