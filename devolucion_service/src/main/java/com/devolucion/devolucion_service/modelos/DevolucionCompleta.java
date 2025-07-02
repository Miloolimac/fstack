package com.devolucion.devolucion_service.modelos;

import java.time.LocalDateTime;
import java.util.List;

public class DevolucionCompleta {
    private int id;
    private LocalDateTime fecha;
    private int usuarioId;
    private int boletaId;
    private String motivo;
    private String estado;
    private List<DetalleDevolucionCompleto> producto;

    public DevolucionCompleta() {}

    public DevolucionCompleta(int id, LocalDateTime fecha, int usuarioId, int boletaId, String motivo, String estado, List<DetalleDevolucionCompleto> producto) {
        this.id = id;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
        this.boletaId = boletaId;
        this.motivo = motivo;
        this.estado = estado;
        this.producto = producto;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public int getBoletaId() {
        return boletaId;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getEstado() {
        return estado;
    }

    public List<DetalleDevolucionCompleto> getProducto() {
        return producto;
    }
}