package proyecto.com.repository;

import proyecto.com.model.Reservacion;
import java.util.List;

public interface ReservacionRepository {
    List<Reservacion> listar();                    // desde V_Reservas_Detalle
    Reservacion obtenerPorId(Long id);             // buscar por ID en Reservaciones
    String insertar(Reservacion r);                // llama SP_INSERT_RESERVA
    String actualizar(Reservacion r);              // llama SP_UPDATE_RESERVA
    String eliminar(Long idReserva);               // llama SP_DELETE_RESERVA
    Reservacion buscarPorIdEnVista(Long id);       // buscar en la vista (opcional)
}
