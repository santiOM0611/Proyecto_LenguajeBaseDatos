package proyecto.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import proyecto.com.model.DetalleFactura;
import proyecto.com.repository.DetalleFacturaRepository;
import proyecto.com.service.DetalleFacturaService;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleFacturaServiceImpl implements DetalleFacturaService {

    @Autowired
    private DetalleFacturaRepository repository;

    @Override
    public List<DetalleFactura> listarTodos() {
        return repository.listar();
    }

    @Override
    public DetalleFactura guardar(DetalleFactura detalleFactura) {
        try {
            String resultado;

            if (detalleFactura.getId() == null || detalleFactura.getId() == 0) {
                System.out.println("=== AGREGANDO NUEVO DETALLE FACTURA ===");
                System.out.println("ID Factura: " + detalleFactura.getIdFactura());
                System.out.println("ID Tipo Habitación: " + detalleFactura.getIdTipoHabitacion());
                System.out.println("ID Servicios: " + detalleFactura.getIdServicios());
                System.out.println("Monto Total: " + detalleFactura.getMontoTotal());

                resultado = repository.agregar(detalleFactura);
                System.out.println("Resultado agregado: " + resultado);
            } else {
                System.out.println("=== EDITANDO DETALLE FACTURA ===");
                System.out.println("ID Detalle Factura: " + detalleFactura.getId());

                resultado = repository.editar(detalleFactura);
                System.out.println("Resultado editado: " + resultado);
            }

            if (resultado != null && resultado.toLowerCase().contains("error")) {
                if (resultado.toLowerCase().contains("clave principal no encontrada") ||
                    resultado.toLowerCase().contains("integridad") ||
                    resultado.toLowerCase().contains("foreign key") ||
                    resultado.toLowerCase().contains("parent key")) {
                    throw new DataIntegrityViolationException(
                        "Error de integridad referencial en los IDs proporcionados"
                    );
                }
                throw new RuntimeException("Error al guardar: " + resultado);
            }

            System.out.println("Detalle factura guardado exitosamente");

        } catch (DataIntegrityViolationException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("ERROR en guardar detalle factura: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el detalle factura: " + e.getMessage(), e);
        }

        return detalleFactura;
    }

    @Override
    public void eliminar(Long id) {
        try {
            System.out.println("=== ELIMINANDO DETALLE FACTURA ID: " + id + " ===");
            String resultado = repository.eliminar(id);

            if (resultado != null && resultado.toLowerCase().contains("error")) {
                throw new RuntimeException("Error al eliminar: " + resultado);
            }

            System.out.println("Detalle factura eliminado exitosamente");
        } catch (Exception e) {
            System.err.println("ERROR en eliminar detalle factura: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar el detalle factura: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<DetalleFactura> obtenerPorId(Long id) {
        return Optional.ofNullable(repository.obtenerPorId(id));
    }

    @Override
    public DetalleFactura buscarPorId(Long id) {
        return repository.obtenerPorId(id);
    }
}