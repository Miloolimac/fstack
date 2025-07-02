package com.categoria.categoria_service.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.categoria.categoria_service.entidades.Categoria;
import com.categoria.categoria_service.repositorio.CategoriaRepository;

@Service
public class CategoriaServicio {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> getAll() {
        return categoriaRepository.findAll();
    }

    public Categoria getCategoriaById(int id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void deleteById(int id) {
        categoriaRepository.deleteById(id);
    }
    //este es para borrar un categoria
    public void borrarCategoria(int id) {
        categoriaRepository.deleteById(id);
    }
}