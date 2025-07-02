package com.categoria.categoria_service.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.categoria.categoria_service.entidades.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    // Métodos ya incluidos: findAll(), findById(), save(), deleteById(), etc.
}