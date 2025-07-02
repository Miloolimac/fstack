package com.provedor.service.proveedor_service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.provedor.service.proveedor_service.entidades.Proveedor;
import com.provedor.service.proveedor_service.modelos.Producto;
import com.provedor.service.proveedor_service.repositorio.ProveedorRepository;
import com.provedor.service.proveedor_service.servicio.ProveedorService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

public class ProveedorServiceTest {
    @Mock
    private ProveedorRepository proveedorRepository;

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private ProveedorService proveedorService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProductosByProveedorId() {
        // Arrange
        int proveedorId = 1;
        Proveedor proveedor = new Proveedor();
        proveedor.setId(proveedorId);

        Producto producto1 = new Producto();
        producto1.setId(1);
        producto1.setNombre("lorena");

        Producto producto2 = new Producto();
        producto2.setId(2);
        producto2.setNombre("felipe");

        when(proveedorRepository.findById(proveedorId)).thenReturn(Optional.of(proveedor));
        when(restTemplate.exchange(
                eq("http://localhost:8001/producto/proveedor/" + proveedorId),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.ok(Arrays.asList(producto1, producto2)));

        // Act
        List<Producto> productos = proveedorService.getProductos(proveedorId);

        // Assert
        assertEquals(2, productos.size());
        assertEquals("lorena", productos.get(0).getNombre());
    }

    @Test
    public void testGetProductosByProveedorId_ProveedorNoExiste() {
        // Arrange
        int proveedorId = 99;
        when(proveedorRepository.findById(proveedorId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            proveedorService.getProductos(proveedorId);
        });
    }

    @Test
    public void testSaveProveedorConDatosCompletos() {
        // Arrange
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre("Pepe garza");
        proveedor.setDireccion("Calle siempre viva 123");
        proveedor.setTelefono("123456789");
        proveedor.setEmail("pepegarza@test.com");

        when(proveedorRepository.save(proveedor)).thenReturn(proveedor);

        Proveedor resultado = proveedorService.save(proveedor);

        // Assert
        assertNotNull(resultado);
        assertEquals("Pepe garza", resultado.getNombre());
        assertEquals("pepegarza@test.com", resultado.getEmail());
    }

    @Test
    public void testFindByNombre() {
        // Arrange
        String nombre = "Pepe";
        Proveedor proveedor = new Proveedor();
        proveedor.setId(1);
        proveedor.setNombre(nombre);

        when(proveedorRepository.findByNombre(nombre)).thenReturn(Arrays.asList(proveedor));

        // Act
        List<Proveedor> resultado = proveedorRepository.findByNombre(nombre);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals(nombre, resultado.get(0).getNombre());
    }

    @Test
    public void testSaveProveedorConCamposObligatoriosFaltantes() {
        // Arrange
        Proveedor proveedor = new Proveedor(); // Falta nombre que debería ser obligatorio

        // Act & Assert
        assertThrows(ConstraintViolationException.class, () -> {
            proveedorService.save(proveedor);
        });
    }

    @Test
    public void testDeleteProveedorConProductosAsociados() {
        // Arrange
        int proveedorId = 1;
        Proveedor proveedor = new Proveedor();
        proveedor.setId(proveedorId);

        when(proveedorRepository.findById(proveedorId)).thenReturn(Optional.of(proveedor));
        when(restTemplate.exchange(
                eq("http://localhost:8001/producto/proveedor/" + proveedorId),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class))).thenReturn(ResponseEntity.ok(Arrays.asList(new Producto()))); // Tiene
                                                                                                                      // productos

        assertThrows(RuntimeException.class, () -> {
            proveedorService.borrarProveedor(proveedorId);
        });
        verify(proveedorRepository, times(0)).deleteById(proveedorId);
    }
}
