package com.tecnouam.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

public class PrestamoRequestDTO {

    @NotNull(message = "El ID del equipo es obligatorio")
    private Long equipoId;

    @NotBlank(message = "El solicitante es obligatorio")
    private String solicitante;

    @NotNull(message = "La fecha del préstamo es obligatoria")
    private LocalDate fechaPrestamo;

    @NotNull(message = "La fecha prevista de devolución es obligatoria")
    @FutureOrPresent(message = "La devolución no puede ser anterior a hoy")
    private LocalDate fechaPrevistaDevolucion;

    public Long getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(Long equipoId) {
        this.equipoId = equipoId;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaPrevistaDevolucion() {
        return fechaPrevistaDevolucion;
    }

    public void setFechaPrevistaDevolucion(LocalDate fechaPrevistaDevolucion) {
        this.fechaPrevistaDevolucion = fechaPrevistaDevolucion;
    }
}