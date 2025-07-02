package com.sucursal.service.sucursal_service.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sucursal.service.sucursal_service.entidades.Sucursal;
import com.sucursal.service.sucursal_service.repositorio.SucursalRepository;

@Service
public class SucursalServicio {

    @Autowired
    private SucursalRepository sucursalRepository;

    // Lista todas las sucursales
    public List<Sucursal> getAll() {
        return sucursalRepository.findAll();
    }

    // Buscar una sucursal por ID
    public Sucursal getSucursalById(int id) {
        return sucursalRepository.findById(id).orElse(null);

    }

    // Guardar una sucursal
    public Sucursal save(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    // Eliminar una sucursal por ID
    public void deleteById(int id) {
        sucursalRepository.deleteById(id);
    }
}
