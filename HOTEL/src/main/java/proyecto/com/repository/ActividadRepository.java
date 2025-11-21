/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import proyecto.com.model.Actividad;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.List;

@Repository
public class ActividadRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Actividad> rowMapper = (rs, rowNum) -> {
        Actividad a = new Actividad();
        a.setId(rs.getLong("ID_ACTIVIDAD"));
        a.setIdHotel(rs.getLong("ID_HOTEL"));
        a.setNombre(rs.getString("NOMBRE_ACTIVIDAD"));
        a.setDescripcion(rs.getString("DESCRIPCION_ACTIVIDAD"));
        a.setCapacidad(rs.getInt("CAPACIDAD_ACTIVIDAD"));
        a.setRutaImagen(rs.getString("RUTA_IMAGEN"));
        return a;
    };

    public List<Actividad> listar() {
        String sql = "SELECT * FROM V_Actividades_Detalle";
        return jdbcTemplate.query(sql, rowMapper);
    }


    public String insertar(Actividad a) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall(
                    "{call PKG_ACTIVIDADES_FRONT.SP_AGREGAR_ACTIVIDAD(?, ?, ?, ?, ?, ?)}")) {

                cs.setLong(1, a.getIdHotel());
                cs.setString(2, a.getNombre());
                cs.setString(3, a.getDescripcion());
                cs.setInt(4, a.getCapacidad());
                cs.setString(5, a.getRutaImagen());
                cs.registerOutParameter(6, Types.VARCHAR);

                cs.execute();
                return cs.getString(6);
            }
        });
    }

    public String actualizar(Actividad a) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall(
                    "{call PKG_ACTIVIDADES_FRONT.SP_EDITAR_ACTIVIDAD(?, ?, ?, ?, ?, ?, ?)}")) {

                cs.setLong(1, a.getId());
                cs.setLong(2, a.getIdHotel());
                cs.setString(3, a.getNombre());
                cs.setString(4, a.getDescripcion());
                cs.setInt(5, a.getCapacidad());
                cs.setString(6, a.getRutaImagen());
                cs.registerOutParameter(7, Types.VARCHAR);

                cs.execute();
                return cs.getString(7);
            }
        });
    }

    public String eliminar(long idActividad) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall(
                    "{call PKG_ACTIVIDADES_FRONT.SP_ELIMINAR_ACTIVIDAD(?, ?)}")) {

                cs.setLong(1, idActividad);
                cs.registerOutParameter(2, Types.VARCHAR);

                cs.execute();
                return cs.getString(2);
            }
        });
    }
}
