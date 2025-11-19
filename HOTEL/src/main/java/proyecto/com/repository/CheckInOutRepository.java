package proyecto.com.repository;

import proyecto.com.model.CheckInOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import oracle.jdbc.OracleTypes;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CheckInOutRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper para mapear resultados de consultas
    private final RowMapper<CheckInOut> checkRowMapper = new RowMapper<CheckInOut>() {
        @Override
        public CheckInOut mapRow(ResultSet rs, int rowNum) throws SQLException {
            CheckInOut check = new CheckInOut();
            check.setIdCheck(rs.getLong("ID_Check"));
            check.setIdReserva(rs.getLong("ID_Reserva"));
            
            // Manejo de fechas
            if (rs.getDate("Fecha_Entrada") != null) {
                check.setFechaEntrada(rs.getDate("Fecha_Entrada").toLocalDate());
            }
            if (rs.getDate("Fecha_Salida") != null) {
                check.setFechaSalida(rs.getDate("Fecha_Salida").toLocalDate());
            }
            
            // Manejo de horas
            if (rs.getTimestamp("Hora_Entrada") != null) {
                check.setHoraEntrada(rs.getTimestamp("Hora_Entrada").toLocalDateTime().toLocalTime());
            }
            if (rs.getTimestamp("Hora_Salida") != null) {
                check.setHoraSalida(rs.getTimestamp("Hora_Salida").toLocalDateTime().toLocalTime());
            }
            
            // Devolución puede ser null
            check.setDevolucion(rs.getString("Devolucion"));
            
            return check;
        }
    };

    // Listar todos los checks con JOIN
    public List<CheckInOut> listarTodos() {
        String sql = "SELECT C.ID_Check, R.ID_Reserva, C.Fecha_Entrada, C.Fecha_Salida, " +
                     "C.Hora_Entrada, C.Hora_Salida, D.Devolucion " +
                     "FROM Check_in_out C " +
                     "INNER JOIN Reservaciones R ON C.ID_Reserva = R.ID_Reserva " +
                     "LEFT JOIN Check_in_out_Devoluciones D ON C.ID_Check = D.ID_Check " +
                     "ORDER BY C.ID_Check DESC";
        
        return jdbcTemplate.query(sql, checkRowMapper);
    }

    // Buscar por ID usando función
    public CheckInOut buscarPorId(Long idCheck) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withFunctionName("FN_GET_CHECK")
                    .declareParameters(
                        new SqlParameter("P_ID_CHECK", Types.NUMERIC),
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR, checkRowMapper)
                    );

            Map<String, Object> inParams = new HashMap<>();
            inParams.put("P_ID_CHECK", idCheck);

            Map<String, Object> result = jdbcCall.execute(inParams);
            
            @SuppressWarnings("unchecked")
            List<CheckInOut> checks = (List<CheckInOut>) result.get("RETURN");
            
            return (checks != null && !checks.isEmpty()) ? checks.get(0) : null;
            
        } catch (Exception e) {
            System.err.println("Error al buscar check: " + e.getMessage());
            return null;
        }
    }

    // Insertar check usando procedimiento
    public String insertar(CheckInOut check) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName("PKG_CHECK_IN_OUT_FRONT")
                    .withProcedureName("SP_INSERT_CHECK")
                    .declareParameters(
                        new SqlParameter("P_ID_RESERVA", Types.NUMERIC),
                        new SqlParameter("P_FECHA_ENTRADA", Types.DATE),
                        new SqlParameter("P_HORA_ENTRADA", Types.TIMESTAMP),
                        new SqlParameter("P_FECHA_SALIDA", Types.DATE),
                        new SqlParameter("P_HORA_SALIDA", Types.TIMESTAMP),
                        new SqlParameter("P_DEVOLUCION", Types.VARCHAR),
                        new SqlOutParameter("P_MENSAJE", Types.VARCHAR)
                    );

            Map<String, Object> inParams = new HashMap<>();
            inParams.put("P_ID_RESERVA", check.getIdReserva());
            inParams.put("P_FECHA_ENTRADA", java.sql.Date.valueOf(check.getFechaEntrada()));
            inParams.put("P_HORA_ENTRADA", java.sql.Timestamp.valueOf(check.getFechaEntrada().atTime(check.getHoraEntrada())));
            inParams.put("P_FECHA_SALIDA", check.getFechaSalida() != null ? java.sql.Date.valueOf(check.getFechaSalida()) : null);
            inParams.put("P_HORA_SALIDA", check.getHoraSalida() != null ? java.sql.Timestamp.valueOf(check.getFechaSalida().atTime(check.getHoraSalida())) : null);
            inParams.put("P_DEVOLUCION", check.getDevolucion());

            Map<String, Object> result = jdbcCall.execute(inParams);
            return (String) result.get("P_MENSAJE");
            
        } catch (Exception e) {
            throw new RuntimeException("Error al insertar check: " + e.getMessage());
        }
    }

    // Actualizar check usando procedimiento
    public String actualizar(CheckInOut check) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName("PKG_CHECK_IN_OUT_FRONT")
                    .withProcedureName("SP_UPDATE_CHECK")
                    .declareParameters(
                        new SqlParameter("P_ID_CHECK", Types.NUMERIC),
                        new SqlParameter("P_ID_RESERVA", Types.NUMERIC),
                        new SqlParameter("P_FECHA_ENTRADA", Types.DATE),
                        new SqlParameter("P_HORA_ENTRADA", Types.TIMESTAMP),
                        new SqlParameter("P_FECHA_SALIDA", Types.DATE),
                        new SqlParameter("P_HORA_SALIDA", Types.TIMESTAMP),
                        new SqlParameter("P_DEVOLUCION", Types.VARCHAR),
                        new SqlOutParameter("P_MENSAJE", Types.VARCHAR)
                    );

            Map<String, Object> inParams = new HashMap<>();
            inParams.put("P_ID_CHECK", check.getIdCheck());
            inParams.put("P_ID_RESERVA", check.getIdReserva());
            inParams.put("P_FECHA_ENTRADA", java.sql.Date.valueOf(check.getFechaEntrada()));
            inParams.put("P_HORA_ENTRADA", java.sql.Timestamp.valueOf(check.getFechaEntrada().atTime(check.getHoraEntrada())));
            inParams.put("P_FECHA_SALIDA", check.getFechaSalida() != null ? java.sql.Date.valueOf(check.getFechaSalida()) : null);
            inParams.put("P_HORA_SALIDA", check.getHoraSalida() != null ? java.sql.Timestamp.valueOf(check.getFechaSalida().atTime(check.getHoraSalida())) : null);
            inParams.put("P_DEVOLUCION", check.getDevolucion());

            Map<String, Object> result = jdbcCall.execute(inParams);
            return (String) result.get("P_MENSAJE");
            
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar check: " + e.getMessage());
        }
    }

    // Eliminar check usando procedimiento
    public String eliminar(Long idCheck) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName("PKG_CHECK_IN_OUT_FRONT")
                    .withProcedureName("SP_DELETE_CHECK")
                    .declareParameters(
                        new SqlParameter("P_ID_CHECK", Types.NUMERIC),
                        new SqlOutParameter("P_MENSAJE", Types.VARCHAR)
                    );

            Map<String, Object> inParams = new HashMap<>();
            inParams.put("P_ID_CHECK", idCheck);

            Map<String, Object> result = jdbcCall.execute(inParams);
            return (String) result.get("P_MENSAJE");
            
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar check: " + e.getMessage());
        }
    }

    // Listar reservaciones disponibles (para el select)
    public List<Map<String, Object>> listarReservaciones() {
        String sql = "SELECT ID_Reserva, Fecha_Inicio, Fecha_Fin " +
                     "FROM Reservaciones " +
                     "ORDER BY ID_Reserva DESC";
        return jdbcTemplate.queryForList(sql);
    }
}