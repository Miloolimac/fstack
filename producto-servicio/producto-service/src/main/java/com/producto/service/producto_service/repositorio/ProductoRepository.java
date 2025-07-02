package com.producto.service.producto_service.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.producto.service.producto_service.entidades.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Integer> {

    List<Producto> findByUsuarioId(int usuarioId);
}
