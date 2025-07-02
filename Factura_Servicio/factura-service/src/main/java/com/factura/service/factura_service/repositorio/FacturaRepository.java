package com.factura.service.factura_service.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.factura.service.factura_service.entidades.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer> {
    // Retorna facturas asociadas a un usuario específico según su ID
    List<Factura> findByUsuarioId(int usuarioId);

}
