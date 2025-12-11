/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package proyecto.com.service;

import proyecto.com.model.ServicioHabitacion;
import java.util.List;
import java.util.Optional;

public interface ServicioHabitacionService {
    
    List<ServicioHabitacion> listarTodos();
    
    ServicioHabitacion guardar(ServicioHabitacion servicioHabitacion);
    
    void eliminar(Long id);
    
    Optional<ServicioHabitacion> obtenerPorId(Long id);
    
    ServicioHabitacion buscarPorId(Long id);
}