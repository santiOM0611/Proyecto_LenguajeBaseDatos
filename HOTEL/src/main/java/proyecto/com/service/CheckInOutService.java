/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.service;

import proyecto.com.model.CheckInOut;
import java.util.List;
import java.util.Optional;

public interface CheckInOutService {
    List<CheckInOut> listarTodos();
    CheckInOut guardar(CheckInOut check);
    void eliminar(Long id);
    Optional<CheckInOut> obtenerPorId(Long id);
    CheckInOut buscarPorId(Long id);
}