package com.factura.service.factura_service.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.factura.service.factura_service.entidades.Factura;
import com.factura.service.factura_service.modelos.Producto;
import com.factura.service.factura_service.modelos.Usuario;
import com.factura.service.factura_service.modelos.FacturaDetalle;
import com.factura.service.factura_service.repositorio.FacturaRepository;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Obtener todas las facturas registradas
    public List<Factura> getAll() {
        return facturaRepository.findAll();
    }

    // Obtener una factura por su ID
    public Factura getFacturaById(int id) {
        return facturaRepository.findById(id).orElse(null);
    }

    // Guardar o actualizar una factura
    public Factura save(Factura factura) {
        return facturaRepository.save(factura);
    }

    // Obtener facturas asociadas a un usuario específico
    public List<Factura> getFacturasByUsuarioId(int usuarioId) {
        return facturaRepository.findByUsuarioId(usuarioId);
    }

    // Obtener datos del usuario desde usuario-service
    public Usuario getUsuario(int usuarioId) {
        String url = "http://localhost:8080/usuario/" + usuarioId;
        return restTemplate.getForObject(url, Usuario.class);
    }

    // Obtener productos relacionados con un usuario específico
    public List<Producto> getProductosByUsuarioId(int usuarioId) {
        ResponseEntity<List<Producto>> response = restTemplate.exchange(
                "http://localhost:8002/producto/usuario/" + usuarioId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Producto>>() {
                });

        return response.getBody();
    }

    // Obtener detalle de factura con datos del usuario y sus productos
    public FacturaDetalle getFacturaDetalle(int facturaId) {
        Factura factura = getFacturaById(facturaId);
        if (factura == null)
            return null;

        Usuario usuario = getUsuario(factura.getUsuarioId());
        List<Producto> productos = getProductosByFacturaId(facturaId);

        return new FacturaDetalle(factura, usuario, productos);
    }

    // Obtener todas las facturas con detalle completo (usuario y productos)
    public List<FacturaDetalle> getAllFacturasDetalle() {
        List<Factura> facturas = getAll();
        return facturas.stream().map(factura -> {
            Usuario usuario = getUsuario(factura.getUsuarioId());
            List<Producto> productos = getProductosByFacturaId(factura.getId());
            return new FacturaDetalle(factura, usuario, productos);
        }).collect(Collectors.toList());
    }

    // Obtener productos asociados a una factura específica (consumir
    // producto-service)
    public List<Producto> getProductosByFacturaId(int facturaId) {
        ResponseEntity<List<Producto>> response = restTemplate.exchange(
                "http://localhost:8002/producto/factura/" + facturaId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Producto>>() {
                });

        return response.getBody();
    }
}
