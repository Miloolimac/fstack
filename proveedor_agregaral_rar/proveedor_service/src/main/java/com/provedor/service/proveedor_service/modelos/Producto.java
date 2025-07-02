package com.provedor.service.proveedor_service.modelos;

public class Producto {
    private int id;
    private String nombre;
    private String marca;
    private int usuarioId;

    public Producto(int id, String nombre, String marca, int usuarioId) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.usuarioId = usuarioId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Producto() {
    }
}

   
