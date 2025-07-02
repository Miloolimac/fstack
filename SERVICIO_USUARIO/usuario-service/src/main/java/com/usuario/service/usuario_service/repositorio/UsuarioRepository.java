package com.usuario.service.usuario_service.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.usuario.service.usuario_service.entidades.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer>
{
    /*
     * findAll() --> Retorna a todos los usuarios
     * findById(Integer id) --> retorna el usuario segun la id
     * save (Usuario usuario) -->Guarda el nuevo usuario
     * deleteById(Integer id)--> Elimina un usuario según su id
     */
}
