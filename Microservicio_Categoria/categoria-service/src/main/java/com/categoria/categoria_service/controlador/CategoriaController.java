package com.categoria.categoria_service.controlador;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.categoria.categoria_service.entidades.Categoria;
import com.categoria.categoria_service.servicio.CategoriaServicio;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaServicio categoriaServicio;

    // GET /categoria → listar todas las categorías
    @GetMapping
    public ResponseEntity<List<Categoria>> listarTodo() {
        List<Categoria> lista = categoriaServicio.getAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // GET /categoria/{id} → obtener una categoría por id con HATEOAS
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Categoria>> obtenerCategoria(@PathVariable("id") int id) {
        Categoria categoria = categoriaServicio.getCategoriaById(id);
        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Categoria> recurso = EntityModel.of(categoria);
        recurso.add(linkTo(methodOn(CategoriaController.class).obtenerCategoria(id)).withSelfRel());
        recurso.add(linkTo(methodOn(CategoriaController.class).listarTodo()).withRel("todas-las-categorias"));

        return ResponseEntity.ok(recurso);
    }

    // POST /categoria → agregar categoría
    @PostMapping
    public ResponseEntity<Categoria> guardarCategoria(@RequestBody Categoria categoria) {
        Categoria nueva = categoriaServicio.save(categoria);
        return ResponseEntity.ok(nueva);
    }

    // DELETE /categoria/{id} → eliminar por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable("id") int id) {
        categoriaServicio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}