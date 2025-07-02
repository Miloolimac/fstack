package com.boleta.boleta_service.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boleta.boleta_service.entidades.Boleta;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Integer> {
}
