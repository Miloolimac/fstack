package com.usuario.service.usuario_service.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario {
    private int id;
    private String nombre;
    private String email;

    @Id /*Se establece el atributo id como clave primaria
        o identificador */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   /*
    * @GeneratedValue esta indicando que los valores 
    del id se generarán de forma automatica.
    */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Usuario() {
        super();
    }
}
