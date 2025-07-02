package com.devolucion.devolucion_service.controlador;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devolucion.devolucion_service.entidades.Devolucion;
import com.devolucion.devolucion_service.modelos.DevolucionCompleta;
import com.devolucion.devolucion_service.servicio.DevolucionServicio;

@RestController
@RequestMapping("/devolucion")
public class DevolucionController {

    @Autowired
    private DevolucionServicio devolucionServicio;

    // ✅ GET /devolucion → listar todas las devoluciones completas
    @GetMapping
    public ResponseEntity<List<EntityModel<DevolucionCompleta>>> listarTodas() {
        List<DevolucionCompleta> lista = devolucionServicio.getAll();

        List<EntityModel<DevolucionCompleta>> resultado = lista.stream()
                .map(devolucion -> EntityModel.of(devolucion,
                        linkTo(methodOn(DevolucionController.class).obtenerDevolucion(devolucion.getId())).withSelfRel(),
                        linkTo(methodOn(DevolucionController.class).listarTodas()).withRel("todas-las-devoluciones")
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultado);
    }

    // ✅ GET /devolucion/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DevolucionCompleta>> obtenerDevolucion(@PathVariable("id") int id) {
        DevolucionCompleta resultado = devolucionServicio.getById(id);
        if (resultado == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<DevolucionCompleta> recurso = EntityModel.of(resultado);
        recurso.add(linkTo(methodOn(DevolucionController.class).obtenerDevolucion(id)).withSelfRel());
        recurso.add(linkTo(methodOn(DevolucionController.class).listarTodas()).withRel("todas-las-devoluciones"));
        recurso.add(linkTo(methodOn(DevolucionController.class).crearDevolucion(null)).withRel("crear-devolucion"));

        return ResponseEntity.ok(recurso);
    }

    // ✅ POST /devolucion
    @PostMapping
    public ResponseEntity<Devolucion> crearDevolucion(@RequestBody Devolucion devolucion) {
        Devolucion nueva = devolucionServicio.guardar(devolucion);
        return ResponseEntity.ok(nueva);
    }

    // ✅ DELETE /devolucion/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDevolucion(@PathVariable("id") int id) {
        boolean eliminado = devolucionServicio.eliminarPorId(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}