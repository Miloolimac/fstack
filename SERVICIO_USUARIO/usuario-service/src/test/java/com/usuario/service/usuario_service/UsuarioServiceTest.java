package com.usuario.service.usuario_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.usuario.service.usuario_service.entidades.Usuario;
import com.usuario.service.usuario_service.repositorio.UsuarioRepository;
import com.usuario.service.usuario_service.servicio.UsuarioService;

public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll(){
        Usuario u1 = new Usuario();
        u1.setId(1);
        u1.setNombre("Megan");
        u1.setEmail("megancita@duoc.cl");

        Usuario u2 = new Usuario();
        u2.setId(2);
        u2.setNombre("Emmita");
        u2.setEmail("Emmita@duoc.cl");

        //decimos que cuando se llame a findAll(),
        //retorne una lista simulada con los usuarios
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(u1,u2));

        //ejecutamos el metodo a testear
        List<Usuario> resultado = usuarioService.getAll();

        //Verificamos
        assertEquals(2, resultado.size());
        assertEquals("Emmita", resultado.get(1).getNombre());
    }
    
    /////////////////////////////////////////////
    /// testear si un usuario existe
    
    @Test
    public void testGetUsuarioById_Existe(){
        Usuario u = new Usuario();
        u.setId(1);
        u.setNombre("lala");
        u.setEmail("lala@duoc.cl");

        //llamamos al metodo findbyid con el id 1 y se simula el
        //retorno del usuario

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(u));

        //Se ejecuta el método
        Usuario resultado = usuarioService.getUsuarioById(1);

        //se verifica comparando uno de los valores
        assertEquals("lala", resultado.getNombre());

        //comparamos que el usuario exista
        assertNotNull(resultado);
    }
//////////////////// testear si el usuario no existe
    @Test
    public void testGetUsuariobyId_NoExiste(){

        when (usuarioRepository.findById(99)).thenReturn(Optional.empty());

        //se ejecuta el método
        Usuario resultado = usuarioService.getUsuarioById(99);

        //verificamos el resultado
        assertNull(resultado);
    }

    /////////////////////////////////////////
    /// testear el insert de un usuario
    /// 
    @Test
    public void testSave(){
        Usuario u = new Usuario();
        u.setNombre("Kate Winslet");
        u.setEmail("kw@duoc.cl");

        //simulamos que el repositorio guarda y retorna un usuario
        when(usuarioRepository.save(u)).thenReturn(u);

        //se ejecuta el método
        Usuario resultado = usuarioService.save(u);

        // verificamos
        assertEquals("Kate Winslet", resultado.getNombre());        

    }

    ////////////////////// testeo delete
    
    @Test
    public void testDeleteusuarioById(){
        int usuarioId = 10;
        usuarioService.deleteUserById(usuarioId);

        //verificar si el usuario se eliminó
        verify(usuarioRepository, times(1)).deleteById(usuarioId);
    }


    
}
