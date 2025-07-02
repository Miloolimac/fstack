package com.promocion.service.promocion_service.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.promocion.service.promocion_service.entidades.Promocion;
import java.util.Date;
import com.promocion.service.promocion_service.modelos.Producto;
import com.promocion.service.promocion_service.repositorio.PromocionRepository;

@Service
public class PromocionService {
    @Autowired
    private RestTemplate restTemplate;

    public List<Producto> getProductos(int promocionId) {
        ResponseEntity<List<Producto>> response = restTemplate.exchange(
                "http://localhost:8001/producto/promocion/" + promocionId, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Producto>>() {
                });
        return response.getBody();
    }

    @Autowired
    private PromocionRepository promocionRepository;

    public List<Promocion> getAll() {
        return promocionRepository.findAll();
    }

    public Promocion getPromocionById(int id) {
        return promocionRepository.findById(id).orElse(null);
    }

    public Promocion save(Promocion promocion) {
        return promocionRepository.save(promocion);
    }

    public void borrarPromocion(int id) {
        promocionRepository.deleteById(id);
    }

    public List<Promocion> getPromocionesActivas() {
        return promocionRepository.findByActivaTrue();
    }

    public double aplicarDescuento(String codigoPromocion, double montoOriginal) {
        Promocion promocion = promocionRepository.findByCodigo(codigoPromocion);
        if (promocion != null && promocion.isActiva() &&
                new Date().after(promocion.getFechaInicio()) &&
                new Date().before(promocion.getFechaFin())) {
            return montoOriginal * (1 - promocion.getDescuento() / 100);
        }
        return montoOriginal;
    }

    public boolean existePromocion(int id) {
        
        throw new UnsupportedOperationException("Unimplemented method 'existePromocion'");
    }
}