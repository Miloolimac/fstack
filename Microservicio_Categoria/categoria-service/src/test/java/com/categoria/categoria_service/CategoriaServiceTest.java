package com.categoria.categoria_service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.categoria.categoria_service.entidades.Categoria;
import com.categoria.categoria_service.repositorio.CategoriaRepository;
import com.categoria.categoria_service.servicio.CategoriaServicio;

public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaServicio categoriaServicio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // 1. Listar todas
    @Test
    public void testGetAll() {
        Categoria c1 = new Categoria(1, "Hombre", "Para hombres");
        Categoria c2 = new Categoria(2, "Mujer", "Para mujeres");

        when(categoriaRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<Categoria> resultado = categoriaServicio.getAll();
        assertEquals(2, resultado.size());
    }

    // 2. Buscar por ID (existe)
    @Test
    public void testGetById_Existe() {
        Categoria c = new Categoria(1, "Hombre", "Para Hombres");
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(c));

        Categoria resultado = categoriaServicio.getCategoriaById(1);
        assertEquals("Hombre", resultado.getNombre());
    }

    // 3. Buscar por ID (no existe)
    @Test
    public void testGetById_NoExiste() {
        when(categoriaRepository.findById(99)).thenReturn(Optional.empty());
        Categoria resultado = categoriaServicio.getCategoriaById(99);
        assertNull(resultado);
    }

    // 4. Guardar nueva categoría
    @Test
    public void testGuardar() {
        Categoria c = new Categoria(1, "Hombre", "Fragancias para hombres");

        when(categoriaRepository.save(c)).thenReturn(c);

        Categoria resultado = categoriaServicio.save(c);
        assertEquals("Hombre", resultado.getNombre());
    }

    // 5. Eliminar una categoria
    @Test
    public void testEliminarCategoria() {
    int categoriaId = 3;
    categoriaServicio.borrarCategoria(categoriaId);

    // Verifica que se llamó exactamente una vez a deleteById con ese ID
    verify(categoriaRepository, times(1)).deleteById(categoriaId);
    }

    // 6. test para cuando no hay ninguna categoria y devuelve una lista vacia
    @Test
    public void testGetAll_Vacio() {
    when(categoriaRepository.findAll()).thenReturn(Arrays.asList());

    List<Categoria> resultado = categoriaServicio.getAll();
    assertTrue(resultado.isEmpty());
    }
}