package proyecto.com.service;

import proyecto.com.model.DetalleFactura;
import java.util.List;
import java.util.Optional;

public interface DetalleFacturaService {
    List<DetalleFactura> listarTodos();
    DetalleFactura guardar(DetalleFactura detalleFactura);
    void eliminar(Long id);
    Optional<DetalleFactura> obtenerPorId(Long id);
    DetalleFactura buscarPorId(Long id);
}