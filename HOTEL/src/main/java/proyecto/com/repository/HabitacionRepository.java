package proyecto.com.repository;

import proyecto.com.model.Habitacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class HabitacionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Habitacion> rowMapper = (rs, rowNum) -> {
        Habitacion h = new Habitacion();
        h.setIdHabitacion(rs.getInt("ID_HABITACION"));
        h.setIdTipoHabitacion(rs.getInt("ID_TIPO_HABITACION"));
        h.setNumeroHabitacion(rs.getString("NUMERO_HABITACION"));
        h.setIdHotel(rs.getInt("ID_HOTEL"));
        h.setEstadoHabitacion(rs.getString("ESTADO_HABITACION"));
        
        int idReserva = rs.getInt("ID_RESERVA");
        h.setIdReserva(rs.wasNull() ? null : idReserva);

        h.setRutaImagen(rs.getString("RUTA_IMAGEN"));
        return h;
    };

    public List<Habitacion> listar() {
        String sql = "SELECT * FROM HABITACIONES";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Habitacion obtenerPorId(int id) {
        String sql = "SELECT * FROM HABITACIONES WHERE ID_HABITACION = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public String insertar(Habitacion h) {
        return jdbcTemplate.execute((Connection conn) -> {
            CallableStatement cs = conn.prepareCall(
                    "{call PKG_HABITACIONES_FRONT.SP_INSERT_HABITACION(?, ?, ?, ?, ?, ?, ?)}");

            cs.setInt(1, h.getIdTipoHabitacion());
            cs.setString(2, h.getNumeroHabitacion());
            cs.setInt(3, h.getIdHotel());
            cs.setString(4, h.getEstadoHabitacion());

            if (h.getIdReserva() == null)
                cs.setNull(5, Types.INTEGER);
            else
                cs.setInt(5, h.getIdReserva());

            cs.setString(6, h.getRutaImagen());
            cs.registerOutParameter(7, Types.VARCHAR);

            cs.execute();
            return cs.getString(7);
        });
    }

    public String actualizar(Habitacion h) {
        return jdbcTemplate.execute((Connection conn) -> {
            CallableStatement cs = conn.prepareCall(
                    "{call PKG_HABITACIONES_FRONT.SP_UPDATE_HABITACION(?, ?, ?, ?, ?, ?, ?, ?)}");

            cs.setInt(1, h.getIdHabitacion());
            cs.setInt(2, h.getIdTipoHabitacion());
            cs.setString(3, h.getNumeroHabitacion());
            cs.setInt(4, h.getIdHotel());
            cs.setString(5, h.getEstadoHabitacion());

            if (h.getIdReserva() == null)
                cs.setNull(6, Types.INTEGER);
            else
                cs.setInt(6, h.getIdReserva());

            cs.setString(7, h.getRutaImagen());
            cs.registerOutParameter(8, Types.VARCHAR);

            cs.execute();
            return cs.getString(8);
        });
    }

    public String eliminar(int idHabitacion) {
        return jdbcTemplate.execute((Connection conn) -> {

            CallableStatement cs = conn.prepareCall(
                    "{call PKG_HABITACIONES_FRONT.SP_DELETE_HABITACION(?, ?)}");

            cs.setInt(1, idHabitacion);
            cs.registerOutParameter(2, Types.VARCHAR);

            cs.execute();
            return cs.getString(2);
        });
    }
}
