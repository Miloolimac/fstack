package com.sucursal.service.sucursal_service.controlador;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sucursal.service.sucursal_service.entidades.Sucursal;
import com.sucursal.service.sucursal_service.servicio.SucursalServicio;

@RestController
@RequestMapping("/sucursal")
public class SucursalController {

    @Autowired
    private SucursalServicio sucursalServicio;

    // GET /sucursal → listar todas las sucursales
    @GetMapping
    public ResponseEntity<List<Sucursal>> listarTodo() {
        List<Sucursal> lista = sucursalServicio.getAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // GET /sucursal/{id} → obtener una sucursal por id con HATEOAS
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Sucursal>> obtenerSucursal(@PathVariable("id") int id) {
        Sucursal sucursal = sucursalServicio.getSucursalById(id);
        if (sucursal == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Sucursal> recurso = EntityModel.of(sucursal);
        recurso.add(linkTo(methodOn(SucursalController.class).obtenerSucursal(id)).withSelfRel());
        recurso.add(linkTo(methodOn(SucursalController.class).listarTodo()).withRel("todas-las-sucursales"));

        return ResponseEntity.ok(recurso);
    }

    // POST /sucursal → agregar sucursal
    @PostMapping
    public ResponseEntity<Sucursal> guardarSucursal(@RequestBody Sucursal sucursal) {
        Sucursal nueva = sucursalServicio.save(sucursal);
        return ResponseEntity.ok(nueva);
    }

    // DELETE /sucursal/{id} → eliminar por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSucursal(@PathVariable("id") int id) {
        sucursalServicio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
