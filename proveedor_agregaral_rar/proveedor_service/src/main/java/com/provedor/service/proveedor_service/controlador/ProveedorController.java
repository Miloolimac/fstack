package com.provedor.service.proveedor_service.controlador;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.provedor.service.proveedor_service.entidades.Proveedor;
import com.provedor.service.proveedor_service.servicio.ProveedorService;
import com.provedor.service.proveedor_service.modelos.Producto;

@RestController
@RequestMapping("/proveedor")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

   
    @GetMapping
    public ResponseEntity<List<Proveedor>> listarProveedores() {
        List<Proveedor> proveedores = proveedorService.getAll();
        if (proveedores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(proveedores);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Proveedor>> obtenerProveedor(@PathVariable("id") int id) {
        Proveedor proveedor = proveedorService.getProveedorById(id);
        if (proveedor == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Proveedor> recurso = EntityModel.of(proveedor);
        recurso.add(linkTo(methodOn(ProveedorController.class).obtenerProveedor(id)).withSelfRel());
        recurso.add(linkTo(methodOn(ProveedorController.class).listarProveedores()).withRel("todos-los-proveedores"));
        recurso.add(linkTo(methodOn(ProveedorController.class).listarProductos(id)).withRel("productos-del-proveedor"));

        return ResponseEntity.ok(recurso);
    }

    
    @GetMapping("/productos/{proveedorId}")
    public ResponseEntity<List<Producto>> listarProductos(@PathVariable("proveedorId") int id) {
        Proveedor proveedor = proveedorService.getProveedorById(id);
        if (proveedor == null) {
            return ResponseEntity.notFound().build();
        }
        List<Producto> productos = proveedorService.getProductos(id);
        return ResponseEntity.ok(productos);
    }

   
    @PostMapping
    public ResponseEntity<Proveedor> guardarProveedor(@RequestBody Proveedor proveedor) {
        Proveedor nuevoProveedor = proveedorService.save(proveedor);
        return ResponseEntity.ok(nuevoProveedor);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable("id") int id) {
        Proveedor proveedor = proveedorService.getProveedorById(id);
        if (proveedor == null) {
            return ResponseEntity.notFound().build();
        }
        proveedorService.borrarProveedor(id);
        return ResponseEntity.noContent().build();
    }
}