package com.promocion.service.promocion_service.controlador;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.promocion.service.promocion_service.entidades.Promocion;
import com.promocion.service.promocion_service.servicio.PromocionService;
import com.promocion.service.promocion_service.modelos.Producto;

@RestController
@RequestMapping("/promocion")
public class PromocionController {

    @Autowired
    private PromocionService promocionService;

    @GetMapping("/activas")
    public ResponseEntity<List<Promocion>> listarPromocionesActivas() {
        List<Promocion> promociones = promocionService.getPromocionesActivas();
        if (promociones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(promociones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Promocion>> obtenerPromocion(@PathVariable("id") int id) {
        Promocion promocion = promocionService.getPromocionById(id);
        if (promocion == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Promocion> recurso = EntityModel.of(promocion);
        recurso.add(linkTo(methodOn(PromocionController.class).obtenerPromocion(id)).withSelfRel());
        recurso.add(linkTo(methodOn(PromocionController.class).listarPromocionesActivas()).withRel("todas-las-promociones"));
        recurso.add(linkTo(methodOn(PromocionController.class).listarProductosDePromocion(id)).withRel("productos"));

        return ResponseEntity.ok(recurso);
    }

    @PostMapping
    public ResponseEntity<Promocion> crearPromocion(@RequestBody Promocion promocion) {
        Promocion nuevaPromocion = promocionService.save(promocion);
        return ResponseEntity.ok(nuevaPromocion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPromocion(@PathVariable("id") int id) {
        if (!promocionService.existePromocion(id)) {
            return ResponseEntity.notFound().build();
        }
        promocionService.borrarPromocion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{promocionId}/productos")
    public ResponseEntity<List<Producto>> listarProductosDePromocion(@PathVariable("promocionId") int id) {
        if (!promocionService.existePromocion(id)) {
            return ResponseEntity.notFound().build();
        }
        List<Producto> productos = promocionService.getProductos(id);
        return ResponseEntity.ok(productos);
    }
}