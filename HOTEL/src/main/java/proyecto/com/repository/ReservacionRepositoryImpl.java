package proyecto.com.repository;

import proyecto.com.model.Reservacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class ReservacionRepositoryImpl implements ReservacionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcCall spInsert;
    private final SimpleJdbcCall spUpdate;
    private final SimpleJdbcCall spDelete;

    @Autowired
    public ReservacionRepositoryImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.spInsert = new SimpleJdbcCall(dataSource)
                .withCatalogName("PKG_RESERVACIONES_FRONT")
                .withProcedureName("SP_INSERT_RESERVA");

        this.spUpdate = new SimpleJdbcCall(dataSource)
                .withCatalogName("PKG_RESERVACIONES_FRONT")
                .withProcedureName("SP_UPDATE_RESERVA");

        this.spDelete = new SimpleJdbcCall(dataSource)
                .withCatalogName("PKG_RESERVACIONES_FRONT")
                .withProcedureName("SP_DELETE_RESERVA");
    }

    private final RowMapper<Reservacion> reservacionRowMapper = new RowMapper<Reservacion>() {
        @Override
        public Reservacion mapRow(ResultSet rs, int rowNum) throws SQLException {
            Reservacion r = new Reservacion();
            r.setIdReserva(rs.getLong("ID_RESERVA"));
            java.sql.Date fe = rs.getDate("FECHA_ENTRADA");
            if (fe != null) r.setFechaEntrada(fe.toLocalDate());
            java.sql.Date fs = rs.getDate("FECHA_SALIDA");
            if (fs != null) r.setFechaSalida(fs.toLocalDate());
            r.setCedula(rs.getLong("CEDULA"));
            // la vista puede traer ID_TIPO_HABITACION
            try {
                int idTipo = rs.getInt("ID_TIPO_HABITACION");
                if (!rs.wasNull()) r.setIdTipoHabitacion(idTipo);
            } catch (SQLException ex) { /* ignore */ }
            return r;
        }
    };

    @Override
    public List<Reservacion> listar() {
        String sql = "SELECT ID_RESERVA, ID_TIPO_HABITACION, FECHA_ENTRADA, FECHA_SALIDA, CEDULA FROM V_RESERVAS_DETALLE ORDER BY ID_RESERVA";
        return jdbcTemplate.query(sql, reservacionRowMapper);
    }

    @Override
    public Reservacion obtenerPorId(Long id) {
        String sql = "SELECT ID_RESERVA, FECHA_ENTRADA, FECHA_SALIDA, CEDULA FROM RESERVACIONES WHERE ID_RESERVA = ?";
        List<Reservacion> lista = jdbcTemplate.query(sql, new Object[]{id}, reservacionRowMapper);
        return lista.isEmpty() ? null : lista.get(0);
    }

    @Override
    public String insertar(Reservacion r) {
        Map<String, Object> in = new HashMap<>();
        in.put("P_FECHA_ENTRADA", java.sql.Date.valueOf(r.getFechaEntrada()));
        in.put("P_FECHA_SALIDA", java.sql.Date.valueOf(r.getFechaSalida()));
        in.put("P_CEDULA", r.getCedula());
        in.put("P_TIPO_HABITACION", r.getIdTipoHabitacion());
        // salida
        Map<String, Object> out = spInsert.execute(in);
        // el package devuelve P_MENSAJE OUT -> SimpleJdbcCall devolverá con clave "P_MENSAJE"
        Object msg = out.get("P_MENSAJE");
        return msg != null ? msg.toString() : "Operación realizada";
    }

    @Override
    public String actualizar(Reservacion r) {
        Map<String, Object> in = new HashMap<>();
        in.put("P_ID_RESERVA", r.getIdReserva());
        in.put("P_FECHA_ENTRADA", java.sql.Date.valueOf(r.getFechaEntrada()));
        in.put("P_FECHA_SALIDA", java.sql.Date.valueOf(r.getFechaSalida()));
        in.put("P_CEDULA", r.getCedula());
        in.put("P_TIPO_HABITACION", r.getIdTipoHabitacion());
        Map<String, Object> out = spUpdate.execute(in);
        Object msg = out.get("P_MENSAJE");
        return msg != null ? msg.toString() : "Operación realizada";
    }

    @Override
    public String eliminar(Long idReserva) {
        Map<String, Object> in = new HashMap<>();
        in.put("P_ID_RESERVA", idReserva);
        Map<String, Object> out = spDelete.execute(in);
        Object msg = out.get("P_MENSAJE");
        return msg != null ? msg.toString() : "Operación realizada";
    }

    @Override
    public Reservacion buscarPorIdEnVista(Long id) {
        String sql = "SELECT ID_RESERVA, ID_TIPO_HABITACION, FECHA_ENTRADA, FECHA_SALIDA, CEDULA FROM V_RESERVAS_DETALLE WHERE ID_RESERVA = ?";
        List<Reservacion> lista = jdbcTemplate.query(sql, new Object[]{id}, reservacionRowMapper);
        return lista.isEmpty() ? null : lista.get(0);
    }
}