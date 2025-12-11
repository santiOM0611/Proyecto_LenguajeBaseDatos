/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.repository;

import proyecto.com.model.CheckInOut;
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
public class CheckInOutRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<CheckInOut> rowMapper = (rs, rowNum) -> {
        CheckInOut c = new CheckInOut();
        c.setIdCheck(rs.getLong("ID_Check"));
        c.setIdReserva(rs.getLong("ID_Reserva"));
        c.setFechaEntrada(rs.getDate("Fecha_Entrada") != null ? 
            rs.getDate("Fecha_Entrada").toLocalDate() : null);
        c.setHoraEntrada(rs.getTime("Hora_Entrada") != null ? 
            rs.getTime("Hora_Entrada").toLocalTime() : null);
        c.setFechaSalida(rs.getDate("Fecha_Salida") != null ? 
            rs.getDate("Fecha_Salida").toLocalDate() : null);
        c.setHoraSalida(rs.getTime("Hora_Salida") != null ? 
            rs.getTime("Hora_Salida").toLocalTime() : null);
        c.setDevolucion(rs.getBigDecimal("Devolucion"));
        return c;
    };

    public List<CheckInOut> listar() {
        String sql = """
            SELECT
                C.ID_Check,
                R.ID_Reserva,
                C.Fecha_Entrada,
                C.Fecha_Salida,
                C.Hora_Entrada,
                C.Hora_Salida,
                D.Devolucion
            FROM Check_in_out C
            INNER JOIN Reservaciones R
                ON C.ID_Reserva = R.ID_Reserva
            LEFT JOIN Check_in_out_Devoluciones D
                ON C.ID_Check = D.ID_Check
        """;
        return jdbcTemplate.query(sql, rowMapper);
    }

    public CheckInOut obtenerPorId(Long idCheck) {
        return jdbcTemplate.execute((Connection conn) -> {
            String sql = """
                SELECT
                    C.ID_Check,
                    R.ID_Reserva,
                    C.Fecha_Entrada,
                    C.Fecha_Salida,
                    C.Hora_Entrada,
                    C.Hora_Salida,
                    D.Devolucion
                FROM Check_in_out C
                INNER JOIN Reservaciones R
                    ON C.ID_Reserva = R.ID_Reserva
                LEFT JOIN Check_in_out_Devoluciones D
                    ON C.ID_Check = D.ID_Check
                WHERE C.ID_Check = ?
            """;
            
            try (var ps = conn.prepareStatement(sql)) {
                ps.setLong(1, idCheck);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs != null && rs.next()) {
                        CheckInOut c = new CheckInOut();
                        c.setIdCheck(rs.getLong("ID_Check"));
                        c.setIdReserva(rs.getLong("ID_Reserva"));
                        c.setFechaEntrada(rs.getDate("Fecha_Entrada") != null ? 
                            rs.getDate("Fecha_Entrada").toLocalDate() : null);
                        c.setHoraEntrada(rs.getTime("Hora_Entrada") != null ? 
                            rs.getTime("Hora_Entrada").toLocalTime() : null);
                        c.setFechaSalida(rs.getDate("Fecha_Salida") != null ? 
                            rs.getDate("Fecha_Salida").toLocalDate() : null);
                        c.setHoraSalida(rs.getTime("Hora_Salida") != null ? 
                            rs.getTime("Hora_Salida").toLocalTime() : null);
                        c.setDevolucion(rs.getBigDecimal("Devolucion"));
                        return c;
                    }
                }
            }
            return null;
        });
    }

    public String agregar(CheckInOut c) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall(
                "{call PKG_CHECK_IN_OUT_FRONT.SP_INSERT_CHECK(?,?,?,?,?,?,?)}")) {
                
                cs.setLong(1, c.getIdReserva());
                cs.setDate(2, c.getFechaEntrada() != null ? 
                    java.sql.Date.valueOf(c.getFechaEntrada()) : null);
                cs.setTime(3, c.getHoraEntrada() != null ? 
                    java.sql.Time.valueOf(c.getHoraEntrada()) : null);
                cs.setDate(4, c.getFechaSalida() != null ? 
                    java.sql.Date.valueOf(c.getFechaSalida()) : null);
                cs.setTime(5, c.getHoraSalida() != null ? 
                    java.sql.Time.valueOf(c.getHoraSalida()) : null);
                
                if (c.getDevolucion() != null) {
                    cs.setBigDecimal(6, c.getDevolucion());
                } else {
                    cs.setNull(6, Types.DECIMAL);
                }
                
                cs.registerOutParameter(7, Types.VARCHAR); 

                cs.execute();
                String resultado = cs.getString(7);

                System.out.println("Resultado del SP_INSERT_CHECK: " + resultado);
                return resultado;
            } catch (Exception e) {
                System.err.println("Error en agregar check-in/out: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        });
    }

    public String editar(CheckInOut c) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall(
                "{call PKG_CHECK_IN_OUT_FRONT.SP_UPDATE_CHECK(?,?,?,?,?,?,?,?)}")) {
                
                cs.setLong(1, c.getIdCheck());
                cs.setLong(2, c.getIdReserva());
                cs.setDate(3, c.getFechaEntrada() != null ? 
                    java.sql.Date.valueOf(c.getFechaEntrada()) : null);
                cs.setTime(4, c.getHoraEntrada() != null ? 
                    java.sql.Time.valueOf(c.getHoraEntrada()) : null);
                cs.setDate(5, c.getFechaSalida() != null ? 
                    java.sql.Date.valueOf(c.getFechaSalida()) : null);
                cs.setTime(6, c.getHoraSalida() != null ? 
                    java.sql.Time.valueOf(c.getHoraSalida()) : null);
                
                if (c.getDevolucion() != null) {
                    cs.setBigDecimal(7, c.getDevolucion());
                } else {
                    cs.setNull(7, Types.DECIMAL);
                }
                
                cs.registerOutParameter(8, Types.VARCHAR);

                cs.execute();
                String resultado = cs.getString(8);

                System.out.println("Resultado del SP_UPDATE_CHECK: " + resultado);
                return resultado;
            } catch (Exception e) {
                System.err.println("Error en editar check-in/out: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        });
    }

    public String eliminar(Long idCheck) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall(
                "{call PKG_CHECK_IN_OUT_FRONT.SP_DELETE_CHECK(?,?)}")) {
                
                cs.setLong(1, idCheck);
                cs.registerOutParameter(2, Types.VARCHAR);

                cs.execute();
                String resultado = cs.getString(2);

                System.out.println("Resultado del SP_DELETE_CHECK: " + resultado);
                return resultado;
            } catch (Exception e) {
                System.err.println("Error en eliminar check-in/out: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        });
    }
}