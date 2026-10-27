package com.tecnouam.backend.dto;

import com.tecnouam.backend.entity.EstadoEquipo;

public class EquipoResponseDTO {

    private Long id;
    private String codigoPatrimonial;
    private String nombre;
    private String tipo;
    private EstadoEquipo estado;

    public EquipoResponseDTO(Long id, String codigoPatrimonial,
                             String nombre, String tipo, EstadoEquipo estado) {
        this.id = id;
        this.codigoPatrimonial = codigoPatrimonial;
        this.nombre = nombre;
        this.tipo = tipo;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public String getCodigoPatrimonial() {
        return codigoPatrimonial;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public EstadoEquipo getEstado() {
        return estado;
    }
}