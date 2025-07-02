package com.producto.service.producto_service.modelos;

public class ProductoConCategoria {

    private int productoId;
    private String nombrePerfume;
    private String categoria;

    public ProductoConCategoria() {}

    public ProductoConCategoria(int productoId, String nombrePerfume, String categoria) {
        this.productoId = productoId;
        this.nombrePerfume = nombrePerfume;
        this.categoria = categoria;
    }

    public int getProductoId() { return productoId; }
    public void setProductoId(int productoId) { this.productoId = productoId; }

    public String getNombrePerfume() { return nombrePerfume; }
    public void setNombrePerfume(String nombrePerfume) { this.nombrePerfume = nombrePerfume; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}