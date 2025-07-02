package com.boleta.boleta_service.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boleta.boleta_service.entidades.Boleta;
import com.boleta.boleta_service.modelos.Producto;
import com.boleta.boleta_service.servicio.BoletaServicio;

@RestController
@RequestMapping("/boleta")
public class BoletaController {

    @Autowired
    private BoletaServicio boletaService;

    @PostMapping
    public ResponseEntity<Boleta> guardarBoleta(@RequestBody Boleta boleta) {
        return ResponseEntity.ok(boletaService.save(boleta));
    }

    @GetMapping
    public ResponseEntity<List<Boleta>> listarBoletas() {
        List<Boleta> boletas = boletaService.getAll();
        if(boletas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(boletas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Boleta> obtenerBoleta(@PathVariable("id") int id) {
        Boleta boleta = boletaService.getBoletaById(id);
        if(boleta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(boleta);
    }

@PostMapping("/productos/{boletaId}")
public ResponseEntity<Producto> agregarProducto(
    @PathVariable int boletaId,
    @RequestBody Producto producto) {
    
    // Lógica para guardar el producto
    Producto nuevoProducto = boletaService.agregarProducto(boletaId, producto);
    return ResponseEntity.ok(nuevoProducto);
}

    @GetMapping("/productos/{boletaId}")
    public ResponseEntity<List<Producto>> listarProductos(@PathVariable("boletaId") int id) {
        Boleta boleta = boletaService.getBoletaById(id);
        if(boleta == null) {
            return ResponseEntity.notFound().build();
        }
        List<Producto> productos = boletaService.getProductos(id);
        return ResponseEntity.ok(productos);
    }
    @DeleteMapping("/{id}")
public ResponseEntity<Void> eliminarBoleta(@PathVariable("id") int id) {
    Boleta boleta = boletaService.getBoletaById(id);
    if(boleta == null) {
        return ResponseEntity.notFound().build();
    }
    boletaService.deleteBoletaById(id);
    return ResponseEntity.noContent().build();
}
}
    

