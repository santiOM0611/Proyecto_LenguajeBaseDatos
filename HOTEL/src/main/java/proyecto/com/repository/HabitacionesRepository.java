package proyecto.com.repository;

import proyecto.com.model.Habitaciones;
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
public class HabitacionesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Habitaciones> rowMapper = (rs, rowNum) -> {
        Habitaciones h = new Habitaciones();
        h.setIdTipoHabitacion(rs.getInt("ID_TIPO_HABITACION"));
        h.setNumeroHabitacion(rs.getString("NUMERO_HABITACION"));
        h.setIdHotel(rs.getInt("ID_HOTEL"));
        return h;
    };

    public List<Habitaciones> listar() {
        String sql = "SELECT * FROM V_HABITACIONES_DETALLE";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Habitaciones obtenerPorId(int id) {
        String sql = "SELECT * FROM Habitaciones WHERE ID_Habitacion = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Habitaciones h = new Habitaciones();
            h.setIdHabitacion(rs.getInt("ID_HABITACION"));
            h.setNumeroHabitacion(rs.getString("NUMERO_HABITACION"));
            h.setEstadoHabitacion(rs.getString("ESTADO_HABITACION"));
            h.setIdTipoHabitacion(rs.getInt("ID_TIPO_HABITACION"));
            h.setIdReserva(rs.getInt("ID_RESERVA"));
            h.setIdHotel(rs.getInt("ID_HOTEL"));
            return h;
        }, id);
    }

    public String insertar(Habitaciones h) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall(
                    "{call PKG_HABITACIONES_FRONT.SP_INSERT_HABITACION(?, ?, ?, ?, ?)}"
            )) {

                cs.setInt(1, h.getIdTipoHabitacion());
                cs.setString(2, h.getNumeroHabitacion());
                cs.setInt(3, h.getIdHotel());
                cs.setString(4, h.getEstadoHabitacion());
                cs.registerOutParameter(5, Types.VARCHAR);

                cs.execute();
                return cs.getString(5);
            }
        });
    }

    public String actualizar(Habitaciones h) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall(
                    "{call PKG_HABITACIONES_FRONT.SP_UPDATE_HABITACION(?, ?, ?, ?, ?, ?)}"
            )) {

                cs.setInt(1, h.getIdHabitacion());
                cs.setInt(2, h.getIdTipoHabitacion());
                cs.setString(3, h.getNumeroHabitacion());
                cs.setInt(4, h.getIdHotel());
                cs.setString(5, h.getEstadoHabitacion());
                cs.registerOutParameter(6, Types.VARCHAR);

                cs.execute();
                return cs.getString(6);
            }
        });
    }

    public String eliminar(int idHabitacion) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall(
                    "{call PKG_HABITACIONES_FRONT.SP_DELETE_HABITACION(?, ?)}"
            )) {

                cs.setInt(1, idHabitacion);
                cs.registerOutParameter(2, Types.VARCHAR);

                cs.execute();
                return cs.getString(2);
            }
        });
    }

    public Habitaciones buscarPorIdConFuncion(int id) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{? = call FN_GET_HABITACION(?)}")) {

                cs.registerOutParameter(1, Types.REF_CURSOR);
                cs.setInt(2, id);
                cs.execute();

                ResultSet rs = (ResultSet) cs.getObject(1);

                if (rs != null && rs.next()) {
                    Habitaciones h = new Habitaciones();
                    h.setIdHabitacion(rs.getInt("ID_HABITACION"));
                    h.setNumeroHabitacion(rs.getString("NUMERO_HABITACION"));
                    h.setEstadoHabitacion(rs.getString("ESTADO_HABITACION"));
                    h.setIdTipoHabitacion(rs.getInt("ID_TIPO_HABITACION"));
                    h.setIdReserva(rs.getInt("ID_RESERVA"));
                    h.setIdHotel(rs.getInt("ID_HOTEL"));
                    return h;
                }

                return null;
            }
        });
    }
}
