package proyecto.com.repository;

import proyecto.com.model.ServicioHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioHabitacionRepository extends JpaRepository<ServicioHabitacion, Long> {
    
    @Query(value = "SELECT SH.* FROM SERVICIOS_HABITACIONES SH " +
                   "INNER JOIN TIPOS_HABITACIONES TH ON SH.ID_TIPO_HABITACION = TH.ID_TIPO_HABITACION " +
                   "WHERE SH.ESTADO = 'A' ORDER BY SH.FECHA_CREACION DESC", nativeQuery = true)
    List<ServicioHabitacion> findAllActivos();
    
    @Query(value = "SELECT SH.* FROM SERVICIOS_HABITACIONES SH " +
                   "INNER JOIN TIPOS_HABITACIONES TH ON SH.ID_TIPO_HABITACION = TH.ID_TIPO_HABITACION " +
                   "WHERE SH.ID_TIPO_HABITACION = :idTipo AND SH.ESTADO = 'A'", nativeQuery = true)
    List<ServicioHabitacion> findByIdTipo(@Param("idTipo") Long idTipo);
    
    // Obtener el próximo ID de la secuencia
    @Query(value = "SELECT SEQ_SERVICIOS_HABITACIONES.NEXTVAL FROM DUAL", nativeQuery = true)
    Long obtenerProximoId();
    
    // Procedimiento AGREGAR del paquete FRONT
    @Procedure(procedureName = "PKG_SERVICIOS_HABITACIONES_FRONT.SP_AGREGAR_SERVICIO_HAB")
    String agregarServicioHabitacion(
        @Param("P_ID_SERVICIO_HABITACION") Long idServicioHab,
        @Param("P_ID_TIPO_HABITACION") Long idTipo,
        @Param("P_NOMBRE_SERVICIO") String nombre,
        @Param("P_DESCRIPCION") String descripcion,
        @Param("P_RUTA_IMAGEN") String rutaImagen
    );
    
    // Procedimiento EDITAR del paquete FRONT
    @Procedure(procedureName = "PKG_SERVICIOS_HABITACIONES_FRONT.SP_EDITAR_SERVICIO_HAB")
    String editarServicioHabitacion(
        @Param("P_ID_SERVICIO_HABITACION") Long idServicioHab,
        @Param("P_ID_TIPO_HABITACION") Long idTipo,
        @Param("P_NOMBRE_SERVICIO") String nombre,
        @Param("P_DESCRIPCION") String descripcion,
        @Param("P_RUTA_IMAGEN") String rutaImagen
    );
    
    // Procedimiento ELIMINAR del paquete FRONT
    @Procedure(procedureName = "PKG_SERVICIOS_HABITACIONES_FRONT.SP_ELIMINAR_SERVICIO_HAB")
    String eliminarServicioHabitacion(
        @Param("P_ID_SERVICIO_HABITACION") Long idServicioHab
    );
}