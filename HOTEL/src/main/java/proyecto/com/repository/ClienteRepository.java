package proyecto.com.repository;

import proyecto.com.model.Cliente;
import java.util.List;

public interface ClienteRepository {

    List<Cliente> listar();

    Cliente obtenerPorId(Long cedula);

    String insertar(Cliente cliente);

    String actualizar(Cliente cliente);

    String eliminar(Long cedula);

    Cliente buscarPorIdConFuncion(Long cedula);
}