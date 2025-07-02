package com.producto.service.producto_service.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.producto.service.producto_service.entidades.Producto;
import com.producto.service.producto_service.modelos.ProductoConCategoria;
import com.producto.service.producto_service.servicio.ProductoServicio;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoServicio productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> listarTodo() {
        List<Producto> lista = productoService.getAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable("id") int id) {
        Producto producto = productoService.getProductoById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto) {
        Producto nuevo = productoService.save(producto);
        return ResponseEntity.ok(nuevo);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Producto>> listarPorUsuario(@PathVariable("usuarioId") int id) {
        return ResponseEntity.ok(productoService.byUsuarioId(id));
    }

    @GetMapping("/{id}/categoria")
    public ResponseEntity<ProductoConCategoria> obtenerConCategoria(@PathVariable("id") int id) {
        ProductoConCategoria respuesta = productoService.getProductoConCategoria(id);
        if (respuesta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(respuesta);
    }
}