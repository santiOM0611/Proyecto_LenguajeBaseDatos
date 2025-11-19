/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.com.model.Servicio;
import proyecto.com.repository.ServicioRepository;
import proyecto.com.service.ServicioService;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private ServicioRepository repository;

    @Override
    public List<Servicio> listarTodos() {
        return repository.listar();
    }

    @Override
public Servicio guardar(Servicio servicio) {
    if (servicio.getId() == null || servicio.getId() == 0) {
        repository.agregar(servicio);
    } else {
        repository.editar(servicio);
    }
    return servicio;
}


    @Override
    public void eliminar(Long id) {
        repository.eliminar(id);
    }

    @Override
    public Optional<Servicio> obtenerPorId(Long id) {
        return Optional.ofNullable(repository.obtenerPorId(id));
    }

    @Override
    public Servicio buscarPorId(Long id) {
        return repository.obtenerPorId(id);
    }
}
