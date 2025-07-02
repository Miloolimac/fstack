package com.sucursal.service.sucursal_service;

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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.sucursal.service.sucursal_service.entidades.Sucursal;
import com.sucursal.service.sucursal_service.repositorio.SucursalRepository;
import com.sucursal.service.sucursal_service.servicio.SucursalServicio;

public class SucursalServicioTest {

	@Mock
	private SucursalRepository sucursalRepository;

	@InjectMocks
	private SucursalServicio sucursalServicio;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	// Test 1: Obtener todas las sucursales
	@Test
	public void testGetAll() {
		Sucursal s1 = new Sucursal();
		s1.setId(1);
		s1.setNombre("Sucursal 1");

		Sucursal s2 = new Sucursal();
		s2.setId(2);
		s2.setNombre("Sucursal 2");

		when(sucursalRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

		List<Sucursal> resultado = sucursalServicio.getAll();

		assertEquals(2, resultado.size());
		assertEquals("Sucursal 1", resultado.get(0).getNombre());
	}

	// Test 2: Obtener sucursal por ID (existe)
	@Test
	public void testGetSucursalByIdExiste() {
		Sucursal s = new Sucursal();
		s.setId(1);
		s.setNombre("Sucursal Existente");

		when(sucursalRepository.findById(1)).thenReturn(Optional.of(s));

		Sucursal resultado = sucursalServicio.getSucursalById(1);

		assertNotNull(resultado);
		assertEquals("Sucursal Existente", resultado.getNombre());
	}

	// Test 3: Obtener sucursal por ID (no existe)
	@Test
	public void testGetSucursalByIdNoExiste() {
		when(sucursalRepository.findById(99)).thenReturn(Optional.empty());

		Sucursal resultado = sucursalServicio.getSucursalById(99);

		assertNull(resultado);
	}

	// Test 4: Guardar sucursal
	@Test
	public void testGuardarSucursal() {
		Sucursal s = new Sucursal();
		s.setNombre("Sucursal Nueva");

		when(sucursalRepository.save(s)).thenReturn(s);

		Sucursal resultado = sucursalServicio.save(s);

		assertNotNull(resultado);
		assertEquals("Sucursal Nueva", resultado.getNombre());
	}

	// Test 5: Eliminar sucursal por ID
	@Test
	public void testEliminarSucursal() {
		doNothing().when(sucursalRepository).deleteById(1);

		sucursalServicio.deleteById(1);

		verify(sucursalRepository, times(1)).deleteById(1);
	}

	// Test 6:probar que getAll devuelve lista vacía cuando no hay
	// sucursales
	@Test
	public void testGetAllVacio() {
		when(sucursalRepository.findAll()).thenReturn(Arrays.asList());

		List<Sucursal> resultado = sucursalServicio.getAll();

		assertTrue(resultado.isEmpty());
	}
}
