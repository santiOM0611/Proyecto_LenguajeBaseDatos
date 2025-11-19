package proyecto.com.service;

import proyecto.com.model.TipoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoHabitacionRepository extends JpaRepository<TipoHabitacion, Long> {
    
    @Query(value = "SELECT * FROM TIPOS_HABITACIONES WHERE ESTADO = 'A' ORDER BY NOMBRE_TIPO_HABITACION", nativeQuery = true)
    List<TipoHabitacion> findAllActivos();
}