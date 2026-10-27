package com.tecnouam.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "equipos")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigoPatrimonial;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEquipo estado = EstadoEquipo.DISPONIBLE;

    @Column(nullable = false)
    private boolean eliminado = false;

    public Long getId() { return id; }

    public String getCodigoPatrimonial() { return codigoPatrimonial; }
    public void setCodigoPatrimonial(String codigoPatrimonial) {
        this.codigoPatrimonial = codigoPatrimonial;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public EstadoEquipo getEstado() { return estado; }
    public void setEstado(EstadoEquipo estado) {
        this.estado = estado;
    }

    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
}