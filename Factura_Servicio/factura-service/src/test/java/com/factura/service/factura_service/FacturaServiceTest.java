package com.factura.service.factura_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.factura.service.factura_service.entidades.Factura;
import com.factura.service.factura_service.modelos.Usuario;
import com.factura.service.factura_service.repositorio.FacturaRepository;
import com.factura.service.factura_service.servicio.FacturaService;

/**
 * Clase de pruebas unitarias para el servicio FacturaService.
 * Cada test valida una funcionalidad clave del microservicio de facturación.
 */
public class FacturaServiceTest {

    @Mock
    private FacturaRepository facturaRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FacturaService facturaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test 1: Validar que se obtengan todas las facturas registradas.
     * Se espera que el servicio devuelva la lista completa de facturas mockeadas.
     */
    @Test
    public void testGetAll() {
        Factura f1 = new Factura(1, "2024-06-01", 5000, 1);
        Factura f2 = new Factura(2, "2024-06-02", 3000, 2);

        when(facturaRepository.findAll()).thenReturn(Arrays.asList(f1, f2));

        List<Factura> resultado = facturaService.getAll();

        assertEquals(2, resultado.size());
        assertEquals("2024-06-02", resultado.get(1).getFechaEmision());
    }

    /**
     * Test 2: Validar que se obtenga una factura por ID existente.
     * Se espera que el servicio devuelva la factura correspondiente al ID.
     */
    @Test
    public void testGetFacturaByIdExiste() {
        Factura factura = new Factura(1, "2024-06-03", 2000, 1);
        when(facturaRepository.findById(1)).thenReturn(Optional.of(factura));

        Factura resultado = facturaService.getFacturaById(1);

        assertNotNull(resultado);
        assertEquals(2000, resultado.getTotal());
    }

    /**
     * Test 3: Validar que se retorne null cuando la factura no existe.
     * Se espera que el servicio devuelva null si no se encuentra la factura.
     */
    @Test
    public void testGetFacturaByIdNoExiste() {
        when(facturaRepository.findById(999)).thenReturn(Optional.empty());

        Factura resultado = facturaService.getFacturaById(999);

        assertNull(resultado);
    }

    /**
     * Test 4: Validar el guardado de una nueva factura.
     * Se espera que la factura sea correctamente guardada y retornada.
     */
    @Test
    public void testGuardarFactura() {
        Factura factura = new Factura(0, "2024-06-05", 8000, 2);
        when(facturaRepository.save(factura)).thenReturn(factura);

        Factura resultado = facturaService.save(factura);

        assertNotNull(resultado);
        assertEquals(8000, resultado.getTotal());
    }

    /**
     * Test 5: Validar que se obtengan las facturas asociadas a un usuario
     * específico.
     * Se espera que el servicio devuelva las facturas del usuario solicitado.
     */
    @Test
    public void testByUsuarioId() {
        Factura f1 = new Factura(1, "2024-06-01", 5000, 5);
        Factura f2 = new Factura(2, "2024-06-02", 4000, 5);

        when(facturaRepository.findByUsuarioId(5)).thenReturn(Arrays.asList(f1, f2));

        List<Factura> resultado = facturaService.getFacturasByUsuarioId(5);

        assertEquals(2, resultado.size());
        assertEquals(5, resultado.get(0).getUsuarioId());
    }

    /**
     * Test 6: Validar que se obtengan los datos del usuario desde el microservicio
     * de usuario.
     * Se espera que los datos mockeados del usuario sean correctamente retornados.
     */
    @Test
    public void testGetUsuario() {
        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(3);
        usuarioMock.setNombre("Carlos");
        usuarioMock.setEmail("carlos@correo.com");

        when(restTemplate.getForObject("http://localhost:8080/usuario/3", Usuario.class))
                .thenReturn(usuarioMock);

        Usuario resultado = facturaService.getUsuario(3);

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());
    }
}
