package com.devolucion.devolucion_service.servicio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.devolucion.devolucion_service.entidades.DetalleDevolucion;
import com.devolucion.devolucion_service.entidades.Devolucion;
import com.devolucion.devolucion_service.modelos.DetalleDevolucionCompleto;
import com.devolucion.devolucion_service.modelos.DevolucionCompleta;
import com.devolucion.devolucion_service.modelos.Producto;
import com.devolucion.devolucion_service.repositorio.DetalleDevolucionRepository;
import com.devolucion.devolucion_service.repositorio.DevolucionRepository;

@Service
public class DevolucionServicio {

    @Autowired
    private DevolucionRepository devolucionRepository;

    @Autowired
    private DetalleDevolucionRepository detalleRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Guardar devolución
    public Devolucion guardar(Devolucion devolucion) {
    devolucion.setFecha(LocalDateTime.now());
    devolucion.setEstado("pendiente");
        if (devolucion.getProducto() == null) {
            devolucion.setProducto(new ArrayList<>());
        }
    Devolucion guardada = devolucionRepository.save(devolucion);
    for (DetalleDevolucion detalle : devolucion.getProducto()) {
        detalle.setDevolucion(guardada);
        detalleRepository.save(detalle);
    }
    return guardada;
    }

    // Obtener devolución por ID con detalles y producto
    public DevolucionCompleta getById(int id) {
        Optional<Devolucion> op = devolucionRepository.findById(id);
        if (!op.isPresent()) return null;

        Devolucion d = op.get();

        List<DetalleDevolucionCompleto> detalles = d.getProducto().stream().map(det -> {
            Producto prod = null;
            try {
                prod = restTemplate.getForObject("http://localhost:8002/producto/" + det.getProductoId(), Producto.class);
            } catch (Exception e) {
                // Producto no encontrado o error en producto-service
            }
            String nombre = (prod != null) ? prod.getNombre() : "Producto no encontrado";
            return new DetalleDevolucionCompleto(det.getProductoId(), nombre, det.getMotivoDetalle());
        }).collect(Collectors.toList());

        return new DevolucionCompleta(
                d.getId(),
                d.getFecha(),
                d.getUsuarioId(),
                d.getBoletaId(),
                d.getMotivo(),
                d.getEstado(),
                detalles
        );
    }

    // Obtener todas las devoluciones completas
    public List<DevolucionCompleta> getAll() {
        List<Devolucion> lista = devolucionRepository.findAll();

        return lista.stream().map(d -> {
            List<DetalleDevolucionCompleto> detalles = d.getProducto().stream().map(det -> {
                Producto prod = null;
                try {
                    prod = restTemplate.getForObject("http://localhost:8002/producto/" + det.getProductoId(), Producto.class);
                } catch (Exception e) {
                }
                String nombre = (prod != null) ? prod.getNombre() : "Producto no encontrado";
                return new DetalleDevolucionCompleto(det.getProductoId(), nombre, det.getMotivoDetalle());
            }).collect(Collectors.toList());

            return new DevolucionCompleta(
                    d.getId(),
                    d.getFecha(),
                    d.getUsuarioId(),
                    d.getBoletaId(),
                    d.getMotivo(),
                    d.getEstado(),
                    detalles
            );
        }).collect(Collectors.toList());
    }

    // Eliminar devolución por ID
    public boolean eliminarPorId(int id) {
        Optional<Devolucion> op = devolucionRepository.findById(id);
        if (op.isPresent()) {
            devolucionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}