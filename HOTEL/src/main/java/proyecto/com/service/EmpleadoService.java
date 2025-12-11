package proyecto.com.service;

import proyecto.com.model.Empleado;
import proyecto.com.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository repo;

    public List<Empleado> listar() {
        return repo.listar();
    }

    public Empleado obtenerPorId(Long cedula) {
        return repo.obtenerPorId(cedula);
    }

    public String guardarEmpleado(Empleado empleado) {
        return repo.insertar(empleado);
    }

    public String actualizarEmpleado(Empleado empleado) {
        return repo.actualizar(empleado);
    }

    public String eliminar(Long cedula) {
        return repo.eliminar(cedula);
    }

    public Empleado buscarPorId(Long cedula) {
        return repo.buscarPorIdConFuncion(cedula);
    }
}
