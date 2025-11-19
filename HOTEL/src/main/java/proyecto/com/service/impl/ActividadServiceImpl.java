/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.com.model.Actividad;
import proyecto.com.repository.ActividadRepository;
import proyecto.com.service.ActividadService;

import java.util.List;
import java.util.Optional;

@Service
public class ActividadServiceImpl implements ActividadService {

    @Autowired
    private ActividadRepository repository;

    @Override
    public List<Actividad> listarTodas() {
        return repository.findAll();
    }

    @Override
    public Actividad guardar(Actividad actividad) {
        return repository.save(actividad);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Actividad> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Actividad buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

   
    @Override
    public boolean existeHotel(Long idHotel) {
   
        return true;
    }
}

