/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.repository;

import proyecto.com.model.ServicioHabitacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;

@Repository
public class ServicioHabitacionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<ServicioHabitacion> rowMapper = (rs, rowNum) -> {
        ServicioHabitacion sh = new ServicioHabitacion();
        sh.setId(rs.getLong("ID_SERVICIO_HABITACION"));
        sh.setIdTipoHabitacion(rs.getLong("ID_TIPO_HABITACION"));
        sh.setNombre(rs.getString("NOMBRE_SERVICIO_HABITACION"));
        sh.setDescripcion(rs.getString("DESCRIPCION_SERVICIO_HABITACION"));
        sh.setRutaImagen(rs.getString("RUTA_IMAGEN"));
        return sh;
    };

    public List<ServicioHabitacion> listar() {
        String sql = """
            SELECT
                SH.ID_Servicio_Habitacion,
                SH.ID_Tipo_Habitacion,
                SH.Nombre_Servicio_Habitacion,
                SH.Descripcion_Servicio_Habitacion,
                SH.Ruta_Imagen
            FROM Servicios_Habitaciones SH
            INNER JOIN Tipos_Habitaciones TH
                ON SH.ID_Tipo_Habitacion = TH.ID_Tipo_Habitacion
            """;
        return jdbcTemplate.query(sql, rowMapper);
    }

    public ServicioHabitacion obtenerPorId(Long idServicioHabitacion) {
        String sql = """
            SELECT
                ID_Servicio_Habitacion,
                ID_Tipo_Habitacion,
                Nombre_Servicio_Habitacion,
                Descripcion_Servicio_Habitacion,
                Ruta_Imagen
            FROM Servicios_Habitaciones
            WHERE ID_Servicio_Habitacion = ?
            """;
        
        List<ServicioHabitacion> resultados = jdbcTemplate.query(sql, rowMapper, idServicioHabitacion);
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    // --- AGREGAR SERVICIO HABITACION ---
    public String agregar(ServicioHabitacion sh) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{call PKG_SERVICIOS_HABITACIONES_FRONT.SP_AGREGAR_SERVICIO_HAB(?,?,?,?,?,?)}")) {
                cs.setLong(1, sh.getId());
                cs.setLong(2, sh.getIdTipoHabitacion());
                cs.setString(3, sh.getNombre());
                cs.setString(4, sh.getDescripcion());
                cs.setString(5, sh.getRutaImagen());
                cs.registerOutParameter(6, Types.VARCHAR); // P_MENSAJE

                cs.execute();
                String resultado = cs.getString(6);

                System.out.println("Resultado del SP_AGREGAR_SERVICIO_HAB: " + resultado);
                return resultado;
            } catch (Exception e) {
                System.err.println("Error en agregar servicio habitación: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        });
    }

    // --- EDITAR SERVICIO HABITACION ---
    public String editar(ServicioHabitacion sh) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{call PKG_SERVICIOS_HABITACIONES_FRONT.SP_EDITAR_SERVICIO_HAB(?,?,?,?,?,?)}")) {
                cs.setLong(1, sh.getId());
                cs.setLong(2, sh.getIdTipoHabitacion());
                cs.setString(3, sh.getNombre());
                cs.setString(4, sh.getDescripcion());
                cs.setString(5, sh.getRutaImagen());
                cs.registerOutParameter(6, Types.VARCHAR);

                cs.execute();
                String resultado = cs.getString(6);

                System.out.println("Resultado del SP_EDITAR_SERVICIO_HAB: " + resultado);
                return resultado;
            } catch (Exception e) {
                System.err.println("Error en editar servicio habitación: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        });
    }

    // --- ELIMINAR SERVICIO HABITACION ---
    public String eliminar(Long idServicioHabitacion) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{call PKG_SERVICIOS_HABITACIONES_FRONT.SP_ELIMINAR_SERVICIO_HAB(?,?)}")) {
                cs.setLong(1, idServicioHabitacion);
                cs.registerOutParameter(2, Types.VARCHAR);

                cs.execute();
                String resultado = cs.getString(2);

                System.out.println("Resultado del SP_ELIMINAR_SERVICIO_HAB: " + resultado);
                return resultado;
            } catch (Exception e) {
                System.err.println("Error en eliminar servicio habitación: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        });
    }

    // --- GENERAR NUEVO ID ---
    public Long generarNuevoId() {
        String sql = "SELECT NVL(MAX(ID_Servicio_Habitacion), 0) + 1 FROM Servicios_Habitaciones";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
}