/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import proyecto.com.model.ServicioHabitacion;
import proyecto.com.repository.ServicioHabitacionRepository;
import proyecto.com.service.ServicioHabitacionService;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioHabitacionServiceImpl implements ServicioHabitacionService {

    @Autowired
    private ServicioHabitacionRepository repository;

    @Override
    public List<ServicioHabitacion> listarTodos() {
        return repository.listar();
    }

    @Override
    public ServicioHabitacion guardar(ServicioHabitacion servicioHabitacion) {
        try {
            String resultado;

            if (servicioHabitacion.getId() == null || servicioHabitacion.getId() == 0) {
                System.out.println("=== AGREGANDO NUEVO SERVICIO DE HABITACIÓN ===");
                System.out.println("ID Tipo Habitación: " + servicioHabitacion.getIdTipoHabitacion());
                System.out.println("Nombre: " + servicioHabitacion.getNombre());
                System.out.println("Descripción: " + servicioHabitacion.getDescripcion());
                System.out.println("Ruta Imagen: " + servicioHabitacion.getRutaImagen());

                resultado = repository.agregar(servicioHabitacion);
                System.out.println("Resultado agregado: " + resultado);
            } else {
                System.out.println("=== EDITANDO SERVICIO DE HABITACIÓN ===");
                System.out.println("ID Servicio Habitación: " + servicioHabitacion.getId());

                resultado = repository.editar(servicioHabitacion);
                System.out.println("Resultado editado: " + resultado);
            }

            if (resultado != null && resultado.toLowerCase().contains("error")) {
                if (resultado.toLowerCase().contains("clave principal no encontrada") ||
                    resultado.toLowerCase().contains("integridad") ||
                    resultado.toLowerCase().contains("foreign key") ||
                    resultado.toLowerCase().contains("parent key")) {
                    throw new DataIntegrityViolationException(
                        "El tipo de habitación con ID " + servicioHabitacion.getIdTipoHabitacion() + " no existe en el sistema"
                    );
                }
                throw new RuntimeException("Error al guardar: " + resultado);
            }

            System.out.println("Servicio de habitación guardado exitosamente");

        } catch (DataIntegrityViolationException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("ERROR en guardar servicio de habitación: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el servicio de habitación: " + e.getMessage(), e);
        }

        return servicioHabitacion;
    }

    @Override
    public void eliminar(Long id) {
        try {
            System.out.println("=== ELIMINANDO SERVICIO DE HABITACIÓN ID: " + id + " ===");
            String resultado = repository.eliminar(id);

            if (resultado != null && resultado.toLowerCase().contains("error")) {
                throw new RuntimeException("Error al eliminar: " + resultado);
            }

            System.out.println("Servicio de habitación eliminado exitosamente");
        } catch (Exception e) {
            System.err.println("ERROR en eliminar servicio de habitación: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar el servicio de habitación: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<ServicioHabitacion> obtenerPorId(Long id) {
        return Optional.ofNullable(repository.obtenerPorId(id));
    }

    @Override
    public ServicioHabitacion buscarPorId(Long id) {
        return repository.obtenerPorId(id);
    }
}