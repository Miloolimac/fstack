package com.devolucion.devolucion_service.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devolucion.devolucion_service.entidades.Devolucion;

@Repository
public interface DevolucionRepository extends JpaRepository<Devolucion, Integer> {
}