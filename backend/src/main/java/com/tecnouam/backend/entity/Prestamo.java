package com.tecnouam.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "equipo_id", nullable = false)
    private Equipo equipo;

    @Column(nullable = false)
    private String solicitante;

    @Column(nullable = false)
    private LocalDate fechaPrestamo;

    @Column(nullable = false)
    private LocalDate fechaPrevistaDevolucion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPrestamo estado = EstadoPrestamo.ACTIVO;

    public Long getId() { return id; }

    public Equipo getEquipo() { return equipo; }
    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public String getSolicitante() { return solicitante; }
    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaPrevistaDevolucion() {
        return fechaPrevistaDevolucion;
    }
    public void setFechaPrevistaDevolucion(LocalDate fecha) {
        this.fechaPrevistaDevolucion = fecha;
    }

    public EstadoPrestamo getEstado() { return estado; }
    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }
}