package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entidades.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

}
