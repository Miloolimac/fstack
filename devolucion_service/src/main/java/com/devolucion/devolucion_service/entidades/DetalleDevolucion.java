package com.devolucion.devolucion_service.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class DetalleDevolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int productoId;

    private String motivoDetalle;

    @ManyToOne
    @JoinColumn(name = "devolucion_id")
    @JsonBackReference
    private Devolucion devolucion;

    public DetalleDevolucion() {}

    public DetalleDevolucion(int productoId, String motivoDetalle) {
        this.productoId = productoId;
        this.motivoDetalle = motivoDetalle;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProductoId() { return productoId; }
    public void setProductoId(int productoId) { this.productoId = productoId; }

    public String getMotivoDetalle() { return motivoDetalle; }
    public void setMotivoDetalle(String motivoDetalle) { this.motivoDetalle = motivoDetalle; }

    public Devolucion getDevolucion() { return devolucion; }
    public void setDevolucion(Devolucion devolucion) { this.devolucion = devolucion; }
}