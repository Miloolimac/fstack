package com.factura.service.factura_service.modelos;

import java.util.List;
import com.factura.service.factura_service.entidades.Factura;

public class FacturaDetalle {
    private Factura factura;
    private Usuario usuario;
    private List<Producto> productos;

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public FacturaDetalle(Factura factura, Usuario usuario, List<Producto> productos) {
        this.factura = factura;
        this.usuario = usuario;
        this.productos = productos;
    }

    // Getters y Setters
}
