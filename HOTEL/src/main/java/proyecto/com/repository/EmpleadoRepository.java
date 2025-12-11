package proyecto.com.repository;

import proyecto.com.model.Empleado;
import java.util.List;

public interface EmpleadoRepository {

    List<Empleado> listar();

    Empleado obtenerPorId(Long cedula);

    String insertar(Empleado empleado);

    String actualizar(Empleado empleado);

    String eliminar(Long cedula);

    Empleado buscarPorIdConFuncion(Long cedula);
}