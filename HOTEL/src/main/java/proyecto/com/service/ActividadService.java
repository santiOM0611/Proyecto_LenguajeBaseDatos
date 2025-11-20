/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.service;

import proyecto.com.model.Actividad;
import java.util.List;
import java.util.Optional;

public interface ActividadService {

    List<Actividad> listarTodas();

    String guardar(Actividad actividad);

    String actualizar(Actividad actividad);

    String eliminar(Long id);

}

