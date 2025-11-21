/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package proyecto.com.service;

import proyecto.com.model.Servicio;
import java.util.List;
import java.util.Optional;

public interface ServicioService {

    List<Servicio> listarTodos();

    Servicio guardar(Servicio servicio);

    void eliminar(Long id);

    Optional<Servicio> obtenerPorId(Long id);

    Servicio buscarPorId(Long id); 
}
