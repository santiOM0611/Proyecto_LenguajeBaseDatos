package proyecto.com.service;

import proyecto.com.model.CheckInOut;
import java.util.List;
import java.util.Map;

public interface CheckInOutService {
    
    List<CheckInOut> listarTodos();
    
    CheckInOut buscarPorId(Long idCheck);
    
    String crear(CheckInOut check);
    
    String actualizar(CheckInOut check);
    
    String eliminar(Long idCheck);
    
    List<Map<String, Object>> listarReservaciones();
}