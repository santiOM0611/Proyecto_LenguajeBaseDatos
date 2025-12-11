package proyecto.com.service;

import proyecto.com.model.Habitacion;
import proyecto.com.repository.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HabitacionService {

    @Autowired
    private HabitacionRepository repo;

    public List<Habitacion> listar() {
        return repo.listar();
    }

    public Habitacion obtenerPorId(int id) {
        return repo.obtenerPorId(id);
    }

    public String insertar(Habitacion h) {
        return repo.insertar(h);
    }

    public String actualizar(Habitacion h) {
        return repo.actualizar(h);
    }

    public String eliminar(int id) {
        return repo.eliminar(id);
    }
}
