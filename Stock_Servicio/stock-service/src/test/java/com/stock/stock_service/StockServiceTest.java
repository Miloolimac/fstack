package com.stock.stock_service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

    import com.stock.stock_service.entidades.Stock;
    import com.stock.stock_service.repositorio.StockRepository;
    import com.stock.stock_service.servicio.StockServicio;

public class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServicio stockServicio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // 1. Listar todo el stock
    @Test
    public void testGetAll() {
        Stock s1 = new Stock(1, 4, 5);
        Stock s2 = new Stock(2, 10, 6);

        when(stockRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<Stock> resultado = stockServicio.getAll();
        assertEquals(2, resultado.size());
    }

    // 2. Buscar por ID (existe)
    @Test
    public void testGetById_Existe() {
        Stock s = new Stock(1, 3, 5);
        when(stockRepository.findById(1)).thenReturn(Optional.of(s));

        Stock resultado = stockServicio.getByProductoId(1);
        assertNotNull(resultado);
        assertEquals(3, resultado.getCantidadActual());
    }

    // 3. Buscar por ID (no existe)
    @Test
    public void testGetById_NoExiste() {
        when(stockRepository.findById(99)).thenReturn(Optional.empty());
        Stock resultado = stockServicio.getByProductoId(99);
        assertNull(resultado);
    }

    // 4. Guardar stock
    @Test
    public void testGuardarStock() {
        Stock s = new Stock(1, 5, 7);
        when(stockRepository.save(s)).thenReturn(s);

        Stock guardado = stockServicio.guardar(s);
        assertEquals(5, guardado.getCantidadActual());
    }

    // 5. Stock crítico (≤ 5)
    @Test
    public void testGetCritico() {
        Stock s1 = new Stock(1, 2, 5);// 2 ≤ 5 -> crítico
        Stock s2 = new Stock(2, 5, 5);// 5 ≤ 5 -> crítico

        when(stockRepository.findByCantidadActualLessThanEqual(5)).thenReturn(Arrays.asList(s1, s2));

        List<Stock> resultado = stockServicio.getCritico();
        assertEquals(2, resultado.size());
    }

    // 6. Stock no crítico (> 5)
    @Test
    public void testGetCritico_Vacio() {
        when(stockRepository.findByCantidadActualLessThanEqual(5)).thenReturn(Arrays.asList());

        List<Stock> resultado = stockServicio.getCritico();
        assertTrue(resultado.isEmpty());
    }
}