package proyecto.com.repository;

import proyecto.com.model.Reservacion;
import java.util.List;

public interface ReservacionRepository {
    List<Reservacion> listar();                    
    Reservacion obtenerPorId(Long id);             
    String insertar(Reservacion r);                
    String actualizar(Reservacion r);             
    String eliminar(Long idReserva);              
    Reservacion buscarPorIdEnVista(Long id);       
}
