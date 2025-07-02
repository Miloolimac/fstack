package com.devolucion.devolucion_service.modelos;

public class DetalleDevolucionCompleto {
    private int productoId;
    private String nombre;
    private String motivoDetalle;

    public DetalleDevolucionCompleto() {}

    public DetalleDevolucionCompleto(int productoId, String nombre, String motivoDetalle) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.motivoDetalle = motivoDetalle;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMotivoDetalle() {
        return motivoDetalle;
    }

    public void setMotivoDetalle(String motivoDetalle) {
        this.motivoDetalle = motivoDetalle;
    }
}