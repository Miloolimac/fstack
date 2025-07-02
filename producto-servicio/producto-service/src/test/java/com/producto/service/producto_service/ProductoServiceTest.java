package com.producto.service.producto_service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.producto.service.producto_service.entidades.Producto;
import com.producto.service.producto_service.repositorio.ProductoRepository;
import com.producto.service.producto_service.servicio.ProductoServicio;

public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServicio productoServicio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    //Listar todos los productos
    @Test
    public void testGetAll() {
        Producto p1 = new Producto();
        p1.setId(1);
        p1.setNombre("Invictus");
        p1.setMarca("Paco Rabanne");

        Producto p2 = new Producto();
        p2.setId(2);
        p2.setNombre("212 VIP");
        p2.setMarca("Carolina Herrera");

        when(productoRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Producto> resultado = productoServicio.getAll();
        assertEquals(2, resultado.size());
        assertEquals("212 VIP", resultado.get(1).getNombre());
    }

    //Buscar producto por ID (existe)
    @Test
    public void testGetProductoById_Existe() {
        Producto p = new Producto();
        p.setId(1);
        p.setNombre("Le Male");

        when(productoRepository.findById(1)).thenReturn(Optional.of(p));

        Producto resultado = productoServicio.getProductoById(1);
        assertNotNull(resultado);
        assertEquals("Le Male", resultado.getNombre());
    }

    //Buscar producto por ID (no existe)
    @Test
    public void testGetProductoById_NoExiste() {
        when(productoRepository.findById(99)).thenReturn(Optional.empty());

        Producto resultado = productoServicio.getProductoById(99);
        assertNull(resultado);
    }

    //Guardar producto
    @Test
    public void testSaveProducto() {
        Producto p = new Producto();
        p.setNombre("One Million");
        p.setMarca("Paco Rabanne");

        when(productoRepository.save(p)).thenReturn(p);

        Producto guardado = productoServicio.save(p);
        assertEquals("One Million", guardado.getNombre());
        assertEquals("Paco Rabanne", guardado.getMarca());
    }

}