package com.stock.stock_service.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.stock_service.entidades.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
    
    //retorna productos con stock crítico (igual o menor al stock mínimo)
    List<Stock> findByCantidadActualLessThanEqual(int cantidad);

}