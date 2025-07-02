package com.factura.service.factura_service.controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.factura.service.factura_service.entidades.Factura;
import com.factura.service.factura_service.modelos.Producto;
import com.factura.service.factura_service.modelos.Usuario;
import com.factura.service.factura_service.servicio.FacturaService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/factura")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public ResponseEntity<List<Factura>> listarFacturas() {
        List<Factura> facturas = facturaService.getAll();
        return ResponseEntity.ok(facturas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Factura>> obtenerFactura(@PathVariable("id") int id) {
        Factura factura = facturaService.getFacturaById(id);
        if (factura == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Factura> recurso = EntityModel.of(factura);

        // enlaces de HATEOAS
        recurso.add(linkTo(methodOn(FacturaController.class).obtenerFactura(id)).withSelfRel());
        recurso.add(linkTo(methodOn(FacturaController.class).listarFacturas()).withRel("todos-los-usuarios"));
        recurso.add(linkTo(methodOn(FacturaController.class).listarProductos(factura.getUsuarioId()))
                .withRel("productos-del-usuario"));

        return ResponseEntity.ok(recurso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable("id") int id) {
        facturaService.getFacturasByUsuarioId(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Factura> guardarFactura(@RequestBody Factura factura) {
        Factura nuevaFactura = facturaService.save(factura);
        return ResponseEntity.ok(nuevaFactura);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Factura>> listarPorUsuarioId(@PathVariable("usuarioId") int usuarioId) {
        List<Factura> facturas = facturaService.getFacturasByUsuarioId(usuarioId);
        return ResponseEntity.ok(facturas);
    }

    // link "productos-del-usuario"
    @GetMapping("/productos/{usuarioId}")
    public ResponseEntity<List<Producto>> listarProductos(@PathVariable("usuarioId") int usuarioId) {
        List<Producto> productos = facturaService.getProductosByUsuarioId(usuarioId);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<Map<String, Object>> obtenerFacturaCompleta(@PathVariable("id") int id) {
        Factura factura = facturaService.getFacturaById(id);
        if (factura == null) {
            return ResponseEntity.notFound().build();
        }

        // Obtener usuario
        Usuario usuario = facturaService.getUsuario(factura.getUsuarioId());

        // Obtener productos por usuarioId
        List<Producto> productos = facturaService.getProductosByUsuarioId(factura.getUsuarioId());

        // Crear respuesta combinada
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("factura", factura);
        resultado.put("usuario", usuario);
        resultado.put("productos", productos);

        return ResponseEntity.ok(resultado);
    }
}