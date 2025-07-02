package com.producto.service.producto_service.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.producto.service.producto_service.entidades.Producto;
import com.producto.service.producto_service.modelos.Categoria;
import com.producto.service.producto_service.modelos.ProductoConCategoria;
import com.producto.service.producto_service.repositorio.ProductoRepository;

@Service
public class ProductoServicio {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Producto> getAll() {
        return productoRepository.findAll();
    }

    public Producto getProductoById(int id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public List<Producto> byUsuarioId(int usuarioId) {
        return productoRepository.findByUsuarioId(usuarioId);
    }

    public ProductoConCategoria getProductoConCategoria(int id) {
        Producto producto = getProductoById(id);
        if (producto == null) return null;

        Categoria categoria = restTemplate.getForObject(
            "http://localhost:8004/categoria/" + producto.getCategoriaId(),
            Categoria.class
        );

        return new ProductoConCategoria(
            producto.getId(),
            producto.getNombre(),
            (categoria != null) ? categoria.getNombre() : "Desconocida"
        );
    }
}