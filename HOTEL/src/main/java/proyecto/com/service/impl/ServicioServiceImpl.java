/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            String resultado;

            if (servicio.getId() == null || servicio.getId() == 0) {
                // Agregar nuevo servicio
                System.out.println("=== AGREGANDO NUEVO SERVICIO ===");
                System.out.println("ID Hotel: " + servicio.getIdHotel());
                System.out.println("Nombre: " + servicio.getNombre());
                System.out.println("Descripción: " + servicio.getDescripcion());
                System.out.println("Costo: " + servicio.getCosto());
                System.out.println("Ruta Imagen: " + servicio.getRutaImagen());

                resultado = repository.agregar(servicio);
                System.out.println("Resultado agregado: " + resultado);
            } else {
                // Editar servicio existente
                System.out.println("=== EDITANDO SERVICIO ===");
                System.out.println("ID Servicio: " + servicio.getId());

                resultado = repository.editar(servicio);
                System.out.println("Resultado editado: " + resultado);
            }

            // Verificar si el resultado indica error de integridad referencial
            if (resultado != null && resultado.toLowerCase().contains("error")) {
                if (resultado.toLowerCase().contains("clave principal no encontrada") ||
                    resultado.toLowerCase().contains("integridad")) {
                    throw new DataIntegrityViolationException(
                        "El hotel con ID " + servicio.getIdHotel() + " no existe en el sistema"
                    );
                }
                throw new RuntimeException("Error al guardar: " + resultado);
            }

            System.out.println("Servicio guardado exitosamente");

        } catch (DataIntegrityViolationException e) {
            // Propagamos para que el controlador lo maneje
            throw e;
        } catch (Exception e) {
            System.err.println("ERROR en guardar servicio: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el servicio: " + e.getMessage(), e);
        }

        return servicio;
    }

    @Override
    public void eliminar(Long id) {
        try {
            System.out.println("=== ELIMINANDO SERVICIO ID: " + id + " ===");
            String resultado = repository.eliminar(id);

            if (resultado != null && resultado.toLowerCase().contains("error")) {
                throw new RuntimeException("Error al eliminar: " + resultado);
            }

            System.out.println("Servicio eliminado exitosamente");
        } catch (Exception e) {
            System.err.println("ERROR en eliminar servicio: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar el servicio: " + e.getMessage(), e);
        }
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
