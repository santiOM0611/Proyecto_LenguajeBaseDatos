package proyecto.com.repository;

import proyecto.com.model.Cliente;
import java.util.List;

public interface ClienteRepository {

    List<Cliente> listar();

    Cliente buscarPorId(Long id);

    void guardar(Cliente cliente);
}
