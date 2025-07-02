package com.promocion.service.promocion_service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.promocion.service.promocion_service.entidades.Promocion;
import com.promocion.service.promocion_service.repositorio.PromocionRepository;
import com.promocion.service.promocion_service.servicio.PromocionService;

public class PromocionServiceTest {
	@Mock
	private PromocionRepository promocionRepository;

	@InjectMocks
	private PromocionService promocionService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAll() {
		Promocion p1 = new Promocion();
		p1.setId(1);
		p1.setCodigo("DESC20");

		Promocion p2 = new Promocion();
		p2.setId(2);
		p2.setCodigo("DESC30");

		when(promocionRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

		List<Promocion> resultado = promocionService.getAll();

		assertEquals(2, resultado.size());
		assertEquals("DESC30", resultado.get(1).getCodigo());
	}

	@Test
	public void testGetPromocionById() {
		Promocion p = new Promocion();
		p.setId(1);
		p.setCodigo("DESC20");

		when(promocionRepository.findById(1)).thenReturn(Optional.of(p));

		Promocion resultado = promocionService.getPromocionById(1);

		assertNotNull(resultado);
		assertEquals("DESC20", resultado.getCodigo());
	}

	@Test
	public void testSave() {
		Promocion p = new Promocion();
		p.setCodigo("DESC20");

		when(promocionRepository.save(p)).thenReturn(p);

		Promocion resultado = promocionService.save(p);

		assertEquals("DESC20", resultado.getCodigo());
	}

	@Test
	public void testAplicarDescuento() {
		Promocion p = new Promocion();
		p.setCodigo("DESC20");
		p.setDescuento(20);
		p.setActiva(true);
		p.setFechaInicio(new Date(System.currentTimeMillis() - 100000));
		p.setFechaFin(new Date(System.currentTimeMillis() + 100000));

		when(promocionRepository.findByCodigo("DESC20")).thenReturn(p);

		double resultado = promocionService.aplicarDescuento("DESC20", 100);
		assertEquals(80, resultado, 0.001);
	}

	@Test
	public void testAplicarDescuentoInactivo() {
		Promocion p = new Promocion();
		p.setCodigo("DESC20");
		p.setDescuento(20);
		p.setActiva(false);

		when(promocionRepository.findByCodigo("DESC20")).thenReturn(p);

		double resultado = promocionService.aplicarDescuento("DESC20", 100);
		assertEquals(100, resultado, 0.001);
	}
}