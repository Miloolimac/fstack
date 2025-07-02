package com.provedor.service.proveedor_service.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.provedor.service.proveedor_service.entidades.Proveedor;
import com.provedor.service.proveedor_service.modelos.Producto;
import com.provedor.service.proveedor_service.repositorio.ProveedorRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;
    
    @Autowired
    private RestTemplate restTemplate;

    public List<Proveedor> getAll() {
        return proveedorRepository.findAll();
    }

    public Proveedor getProveedorById(int id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    public Proveedor save(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public void borrarProveedor(int id) {
        Proveedor proveedor = proveedorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado con ID: " + id));

        if (tieneProductosAsociados(id)) {
            throw new IllegalStateException("No se puede eliminar el proveedor porque tiene productos asociados.");
        }

        proveedorRepository.deleteById(id);
    }

    public List<Producto> getProductos(int proveedorId) {
        if (!proveedorRepository.existsById(proveedorId)) {
            throw new EntityNotFoundException("Proveedor no encontrado con ID: " + proveedorId);
        }

        ResponseEntity<List<Producto>> response = restTemplate.exchange(
            "http://localhost:8001/producto/proveedor/" + proveedorId,
            HttpMethod.GET,
            null, 
            new ParameterizedTypeReference<List<Producto>>() {}
        );

        return response.getBody();
    }

    private boolean tieneProductosAsociados(int proveedorId) {
        List<Producto> productos = getProductos(proveedorId);
        return productos != null && !productos.isEmpty();
    }
}