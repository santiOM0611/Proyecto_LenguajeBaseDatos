package proyecto.com.service;

import proyecto.com.model.Cliente;
import proyecto.com.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public List<Cliente> listar() {
        return repo.listar();
    }

    public Cliente obtenerPorId(Long cedula) {
        return repo.obtenerPorId(cedula);
    }

    public String guardarCliente(Cliente cliente) {
        return repo.insertar(cliente);
    }

    public String actualizarCliente(Cliente cliente) {
        return repo.actualizar(cliente);
    }

    public String eliminar(Long cedula) {
        return repo.eliminar(cedula);
    }
}