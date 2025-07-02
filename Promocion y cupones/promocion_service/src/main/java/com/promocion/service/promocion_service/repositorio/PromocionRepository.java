package com.promocion.service.promocion_service.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.promocion.service.promocion_service.entidades.Promocion;

public interface PromocionRepository extends JpaRepository<Promocion, Integer> {
    List<Promocion> findByActivaTrue();

    Promocion findByCodigo(String codigo);
}