package com.usuario.service.usuario_service.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usuario.service.usuario_service.entidades.Usuario;
import com.usuario.service.usuario_service.modelos.Producto;
import com.usuario.service.usuario_service.repositorio.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private RestTemplate restTemplate;
    public List<Producto> getProductos(int usuarioId){
        List<Producto>productos = restTemplate.getForObject("http://localhost:8002/producto/usuario/"+usuarioId, List.class);
        return productos;
    }

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getAll(){
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(int id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario save(Usuario usuario){
        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        return nuevoUsuario;
    }

    public void deleteUserById(int id){
        usuarioRepository.deleteById(id);
    }

}
