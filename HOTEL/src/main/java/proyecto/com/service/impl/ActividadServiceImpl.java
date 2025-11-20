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


@Service
public class ActividadServiceImpl implements ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;  

    @Override
    public List<Actividad> listarTodas() {
        return actividadRepository.listar();
    }

    @Override
    public String guardar(Actividad actividad) {
        return actividadRepository.insertar(actividad);
    }

    @Override
    public String actualizar(Actividad actividad) {
        return actividadRepository.actualizar(actividad);
    }

    @Override
    public String eliminar(Long id) {
        return actividadRepository.eliminar(id);
    }
}

