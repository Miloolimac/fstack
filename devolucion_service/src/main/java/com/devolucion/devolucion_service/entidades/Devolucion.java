package com.devolucion.devolucion_service.entidades;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Devolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime fecha;

    private int usuarioId;
    private int boletaId;

    private String motivo;

    private String estado = "pendiente";

    @OneToMany(mappedBy = "devolucion", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<DetalleDevolucion> producto;

    public Devolucion() {}

    public Devolucion(LocalDateTime fecha, int usuarioId, int boletaId, String motivo, List<DetalleDevolucion> producto) {
        this.fecha = fecha;
        this.usuarioId = usuarioId;
        this.boletaId = boletaId;
        this.motivo = motivo;
        this.producto = producto;
        this.estado = "pendiente";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public int getBoletaId() { return boletaId; }
    public void setBoletaId(int boletaId) { this.boletaId = boletaId; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<DetalleDevolucion> getProducto() { return producto; }
    public void setProducto(List<DetalleDevolucion> producto) {
        this.producto = producto;
        if (producto != null) {
            for (DetalleDevolucion d : producto) {
                d.setDevolucion(this); // establecer la relación inversa
            }
        }
    }
}