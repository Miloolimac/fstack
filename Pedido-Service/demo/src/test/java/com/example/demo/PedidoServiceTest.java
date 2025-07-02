package com.example.demo;

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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.demo.entidades.Pedido;
import com.example.demo.repositorio.PedidoRepository;
import com.example.demo.servicio.PedidoServicio;

public class PedidoServiceTest {
@Mock
private PedidoRepository pedidoRepository;
@InjectMocks
private PedidoServicio pedidoServicio;
@BeforeEach
public void setup(){
    MockitoAnnotations.openMocks(this);
}

@Test
public void TestGetAll(){
    Pedido p1 = new Pedido();
    p1.setId(1);
    p1.setNombrePedido("shakira mid night");
    p1.setFecha("20/04/2025");
    p1.setDescripcion("purfum purfum");

    Pedido p2 = new Pedido();
    p2.setId(2);
    p2.setNombrePedido("antonio banderas ICON");
    p2.setFecha("12/12/2024");
    p2.setDescripcion("elixir");

    when(pedidoRepository.findAll()).thenReturn(Arrays.asList(p1,p2));

    List<Pedido> resultado = pedidoServicio.getAll();

    assertEquals(2, resultado.size());
    assertEquals("shakira mid night", resultado.get(0).getNombrePedido());
    assertEquals("antonio banderas ICON", resultado.get(1).getNombrePedido());
}
@Test
public void testGetPedidoById_existe(){
    Pedido u = new Pedido();
    u.setId(1);
    u.setNombrePedido("antonio banderas blue seduction");
    u.setFecha("01/01/2025");
    u.setDescripcion("toalet");

    when(pedidoRepository.findById(1)).thenReturn(Optional.of(u));

    Pedido resultado = pedidoServicio.getPedidoById(1);

    assertEquals("antonio banderas blue seduction", resultado.getNombrePedido());

    assertNotNull(resultado);
}

@Test
public void testGetPedidoById_noExiste(){
    when(pedidoRepository.findById(99)).thenReturn(Optional.empty());

    Pedido resultado = pedidoServicio.getPedidoById(99);

    assertNull(resultado);
}

@Test
public void testSave(){
    Pedido u = new Pedido();
    u.setNombrePedido("antonio banderas blue");
    u.setFecha("01/02/2024");
    u.setDescripcion("purfum");

    when(pedidoRepository.save(u)).thenReturn(u);

    Pedido resultado = pedidoServicio.save(u);

    assertEquals("antonio banderas blue", resultado.getNombrePedido());

}

@Test
public void testDeletePedidoById(){
    int id = 10;
    pedidoServicio.deletePedidoById(id);

    verify(pedidoRepository, times(1)).deleteById(id);
}
}
