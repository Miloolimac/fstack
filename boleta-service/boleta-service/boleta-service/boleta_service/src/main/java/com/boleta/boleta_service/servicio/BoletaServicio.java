package com.boleta.boleta_service.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.boleta.boleta_service.entidades.Boleta;
import com.boleta.boleta_service.modelos.Producto;
import com.boleta.boleta_service.repositorio.BoletaRepository;

@Service
public class BoletaServicio {

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Boleta> getAll() {
        return boletaRepository.findAll();
    }

    public Boleta getBoletaById(int id) {
        return boletaRepository.findById(id).orElse(null);
    }

    public Boleta save(Boleta boleta) {
        return boletaRepository.save(boleta);
    }

    public List<Producto> getProductos(int boletaId) {
        List<Producto> productos = restTemplate.getForObject(
            "http://localhost:8005/producto/boleta/" + boletaId,
            List.class);
        return productos;
    }

public Producto agregarProducto(int boletaId, Producto producto) {
    String url = "http://localhost:8005/producto/boleta/" + boletaId;
    return restTemplate.postForObject(url, producto, Producto.class);
}
public void deleteBoletaById(int id){
    boletaRepository.deleteById(id);
}

    
}
