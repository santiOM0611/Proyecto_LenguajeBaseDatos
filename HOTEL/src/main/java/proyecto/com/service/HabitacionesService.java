
package proyecto.com.service;
import proyecto.com.model.Habitaciones;
import proyecto.com.repository.HabitacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

public class HabitacionesService {

    public Object listar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object obtenerPorId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String guardarHabitacion(Habitaciones habitacion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String actualizarHabitacion(Habitaciones habitacion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Habitaciones buscarPorId(int idHabitacion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Service
public class HabitacionService {

    @Autowired
    private HabitacionesRepository repo;

    public List<Habitaciones> listar() {
        return repo.listar();
    }

    public Habitaciones obtenerPorId(int id) {
        return repo.obtenerPorId(id);
    }

    public String guardarHabitacion(Habitaciones habitacion) {
        return repo.insertar(habitacion);
    }

    public String actualizarHabitacion(Habitaciones habitacion) {
        return repo.actualizar(habitacion);
    }

    public String eliminar(int idHabitacion) {
        return repo.eliminar(idHabitacion);
    }

    public Habitaciones buscarPorId(int id) {
        return repo.buscarPorIdConFuncion(id);
    }
}

    
}
