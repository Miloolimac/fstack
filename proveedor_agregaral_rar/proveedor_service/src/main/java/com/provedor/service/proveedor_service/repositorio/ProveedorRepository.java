package com.provedor.service.proveedor_service.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.provedor.service.proveedor_service.entidades.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    List<Proveedor> findByNombre(String nombre);

}
