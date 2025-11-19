package proyecto.com.service;

import proyecto.com.model.Reservacion;
import proyecto.com.repository.ReservacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservacionService {

    @Autowired
    private ReservacionRepository repo;

    public List<Reservacion> listar() {
        return repo.listar();
    }

    public Reservacion obtenerPorId(Long id) {
        return repo.obtenerPorId(id);
    }

    public Reservacion buscarPorIdEnVista(Long id) {
        return repo.buscarPorIdEnVista(id);
    }

    public String guardarReservacion(Reservacion r) {
        return repo.insertar(r);
    }

    public String actualizarReservacion(Reservacion r) {
        return repo.actualizar(r);
    }

    public String eliminarReservacion(Long id) {
        return repo.eliminar(id);
    }
}
