/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import proyecto.com.model.CheckInOut;
import proyecto.com.repository.CheckInOutRepository;
import proyecto.com.service.CheckInOutService;

import java.util.List;
import java.util.Optional;

@Service
public class CheckInOutServiceImpl implements CheckInOutService {

    @Autowired
    private CheckInOutRepository repository;

    @Override
    public List<CheckInOut> listarTodos() {
        return repository.listar();
    }

    @Override
    public CheckInOut guardar(CheckInOut check) {
        try {
            String resultado;

            if (check.getIdCheck() == null || check.getIdCheck() == 0) {
                // Agregar nuevo check-in/out
                System.out.println("=== AGREGANDO NUEVO CHECK-IN/OUT ===");
                System.out.println("ID Reserva: " + check.getIdReserva());
                System.out.println("Fecha Entrada: " + check.getFechaEntrada());
                System.out.println("Hora Entrada: " + check.getHoraEntrada());
                System.out.println("Fecha Salida: " + check.getFechaSalida());
                System.out.println("Hora Salida: " + check.getHoraSalida());
                System.out.println("Devolución: " + check.getDevolucion());

                resultado = repository.agregar(check);
                System.out.println("Resultado agregado: " + resultado);
            } else {
                // Editar check-in/out existente
                System.out.println("=== EDITANDO CHECK-IN/OUT ===");
                System.out.println("ID Check: " + check.getIdCheck());

                resultado = repository.editar(check);
                System.out.println("Resultado editado: " + resultado);
            }

            // Verificar si el resultado indica error de integridad referencial
            if (resultado != null && resultado.toLowerCase().contains("error")) {
                if (resultado.toLowerCase().contains("clave principal no encontrada") ||
                    resultado.toLowerCase().contains("integridad")) {
                    throw new DataIntegrityViolationException(
                        "La reserva con ID " + check.getIdReserva() + " no existe en el sistema"
                    );
                }
                throw new RuntimeException("Error al guardar: " + resultado);
            }

            System.out.println("Check-in/out guardado exitosamente");

        } catch (DataIntegrityViolationException e) {
            // Propagamos para que el controlador lo maneje
            throw e;
        } catch (Exception e) {
            System.err.println("ERROR en guardar check-in/out: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el check-in/out: " + e.getMessage(), e);
        }

        return check;
    }

    @Override
    public void eliminar(Long id) {
        try {
            System.out.println("=== ELIMINANDO CHECK-IN/OUT ID: " + id + " ===");
            String resultado = repository.eliminar(id);

            if (resultado != null && resultado.toLowerCase().contains("error")) {
                throw new RuntimeException("Error al eliminar: " + resultado);
            }

            System.out.println("Check-in/out eliminado exitosamente");
        } catch (Exception e) {
            System.err.println("ERROR en eliminar check-in/out: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar el check-in/out: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<CheckInOut> obtenerPorId(Long id) {
        return Optional.ofNullable(repository.obtenerPorId(id));
    }

    @Override
    public CheckInOut buscarPorId(Long id) {
        return repository.obtenerPorId(id);
    }
}