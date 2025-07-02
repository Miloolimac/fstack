package com.devolucion.devolucion_service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.devolucion.devolucion_service.entidades.DetalleDevolucion;
import com.devolucion.devolucion_service.entidades.Devolucion;
import com.devolucion.devolucion_service.modelos.DevolucionCompleta;
import com.devolucion.devolucion_service.modelos.Producto;
import com.devolucion.devolucion_service.repositorio.DetalleDevolucionRepository;
import com.devolucion.devolucion_service.repositorio.DevolucionRepository;
import com.devolucion.devolucion_service.servicio.DevolucionServicio;

public class DevolucionServiceTest {

    @Mock
    private DevolucionRepository devolucionRepository;

    @Mock
    private DetalleDevolucionRepository detalleRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DevolucionServicio devolucionServicio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // 1. Guardar devolución con detalles
    @Test
    public void testGuardarDevolucion() {
        Devolucion d = new Devolucion();
        d.setUsuarioId(1);
        d.setBoletaId(25);
        d.setMotivo("Producto equivocado");
        d.setProducto(Arrays.asList(new DetalleDevolucion(12, "Motivo 1")));

        when(devolucionRepository.save(any(Devolucion.class))).thenReturn(d);

        Devolucion guardada = devolucionServicio.guardar(d);
        assertEquals("Producto equivocado", guardada.getMotivo());
        assertNotNull(guardada.getProducto());
    }

    // 2. Obtener devolución completa (existe)
    @Test
    public void testObtenerDevolucionCompleta_Existe() {
        Devolucion d = new Devolucion();
        d.setId(1);
        d.setFecha(LocalDateTime.now());
        d.setUsuarioId(1);
        d.setBoletaId(25);
        d.setMotivo("Cambio");
        d.setEstado("pendiente");

        DetalleDevolucion detalle = new DetalleDevolucion(12, "Error en el perfume");
        d.setProducto(Arrays.asList(detalle));

        when(devolucionRepository.findById(1)).thenReturn(Optional.of(d));

        Producto productoSimulado = new Producto(12, "Aqua Di Gio");
        when(restTemplate.getForObject("http://localhost:8002/producto/12", Producto.class)).thenReturn(productoSimulado);

        DevolucionCompleta resultado = devolucionServicio.getById(1);
        assertEquals(1, resultado.getUsuarioId());
        assertEquals("Aqua Di Gio", resultado.getProducto().get(0).getNombre());
    }

    // 3. Obtener devolución (no existe)
    @Test
    public void testObtenerDevolucion_NoExiste() {
        when(devolucionRepository.findById(99)).thenReturn(Optional.empty());
        DevolucionCompleta resultado = devolucionServicio.getById(99);
        assertNull(resultado);
    }

    // 4. Verificar estado inicial
    @Test
    public void testEstadoInicialEsPendiente() {
        Devolucion d = new Devolucion();
        assertEquals("pendiente", d.getEstado());
    }

    // 5. Verificar que se setea fecha automáticamente
    @Test
    public void testFechaSeSeteaEnGuardar() {
        Devolucion d = new Devolucion();
        when(devolucionRepository.save(any(Devolucion.class))).thenAnswer(invoc -> {
            Devolucion dev = invoc.getArgument(0);
            assertNotNull(dev.getFecha());
            return dev;
        });

        devolucionServicio.guardar(d);
    }

    // 6. Verificar nombre en producto no encontrado
    @Test
    public void testNombreProductoNoEncontrado() {
        Devolucion d = new Devolucion();
        d.setId(2);
        d.setFecha(LocalDateTime.now());
        d.setUsuarioId(1);
        d.setBoletaId(30);
        d.setMotivo("Devolución");

        DetalleDevolucion detalle = new DetalleDevolucion(999, "Motivo desconocido");
        d.setProducto(Arrays.asList(detalle));

        when(devolucionRepository.findById(2)).thenReturn(Optional.of(d));
        when(restTemplate.getForObject("http://localhost:8002/producto/999", Producto.class)).thenReturn(null);

        DevolucionCompleta resultado = devolucionServicio.getById(2);
        assertEquals("Producto no encontrado", resultado.getProducto().get(0).getNombre());
    }
}