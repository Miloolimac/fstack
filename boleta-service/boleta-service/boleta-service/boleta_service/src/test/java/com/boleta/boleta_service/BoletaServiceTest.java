package com.boleta.boleta_service;

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

import com.boleta.boleta_service.entidades.Boleta;
import com.boleta.boleta_service.repositorio.BoletaRepository;
import com.boleta.boleta_service.servicio.BoletaServicio;

public class BoletaServiceTest {
    @Mock  //simula datos sin base de datos
    private BoletaRepository boletaRepository;

    @InjectMocks  //permite generar la simulacion
    private BoletaServicio boletaService;

    @BeforeEach  //se ocupa para poder ejecutar
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }
    

    @Test
    public void TestGetAll(){
        Boleta b1 = new Boleta();
        b1.setId(1);
        b1.setCliente("vicente");
        b1.setFecha("12/12/25");
        b1.setTotal(10000);
        b1.setEstado("pagado");

        Boleta b2 = new Boleta();
        b2.setId(2);
        b2.setCliente("sofia");
        b2.setFecha("19/04/24");
        b2.setTotal(250000);
        b2.setEstado("pendiente");

        //decimos que cuando se llame a findAll(),
        //retorne una lista simulada con los usuarios
        when(boletaRepository.findAll()).thenReturn(Arrays.asList(b1,b2));

        //ejecutamos el metodo a testear
        List<Boleta> resultado = boletaService.getAll();

        //Verificamos
        assertEquals(2, resultado.size());
        assertEquals("sofia", resultado.get(1).getCliente());
    }

        /// testear si un usuario existe 
        
    @Test
    public void testGetBoletaById_existe(){
        Boleta u = new Boleta();
        u.setId(1);
        u.setCliente("lala");
        u.setFecha("15/03/25");
        u.setTotal(2000);
        u.setEstado("pagado");

        //llamamos al metodo findbyid con el id 1 y se simula el
        //retorno del usuario

        when(boletaRepository.findById(1)).thenReturn(Optional.of(u));

        //Se ejecuta el método
        Boleta resultado = boletaService.getBoletaById(1);

        //se verifica comparando uno de los valores
        assertEquals("lala", resultado.getCliente());

        //comparamos que el usuario exista
        assertNotNull(resultado);
    }

    //////////////////// testear si el usuario no existe
    @Test
    public void testGetBoletabyId_NoExiste(){
        when (boletaRepository.findById(99)).thenReturn(Optional.empty());

        //se ejecuta el método
        Boleta resultado = boletaService.getBoletaById(99);

        //verificamos el resultado
        assertNull(resultado);

    }
     /////////////////////////////////////////
    /// testear el insert de un usuario
    /// 
    @Test
    public void testSave(){
        Boleta u = new Boleta();
        u.setCliente("vicho");
        u.setFecha("01/01/2025");
        u.setTotal(80000);
        u.setEstado("pendiente");

        //simulamos que el repositorio guarda y retorna un usuario
        when(boletaRepository.save(u)).thenReturn(u);

        //se ejecuta el método
        Boleta resultado = boletaService.save(u);

        // verificamos
        assertEquals("vicho", resultado.getCliente()); 

    }

    ////////////////////// testeo delete
    
    @Test
    public void testDeleteBoletaById(){
        int usuarioId = 10;
        boletaService.deleteBoletaById(usuarioId);

        //verificar si el usuario se eliminó
        verify(boletaRepository, times(1)).deleteById(usuarioId);
    }

}

