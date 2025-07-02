package com.factura.service.factura_service.modelos;

public class Producto {

    private int id;
    private String marca;
    private String nombre;
    private int usuarioId;

    public Producto(int id, String marca, String nombre, int usuarioId) {
        this.id = id;
        this.marca = marca;
        this.nombre = nombre;
        this.usuarioId = usuarioId;
    }

    public Producto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
}