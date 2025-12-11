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
        c.setIdCheck(rs.getLong("ID_CHECK"));
        c.setIdReserva(rs.getLong("ID_RESERVA"));
        c.setFechaEntrada(rs.getDate("FECHA_ENTRADA") != null ? 
            rs.getDate("FECHA_ENTRADA").toLocalDate() : null);
        c.setHoraEntrada(rs.getTime("HORA_ENTRADA") != null ? 
            rs.getTime("HORA_ENTRADA").toLocalTime() : null);
        c.setFechaSalida(rs.getDate("FECHA_SALIDA") != null ? 
            rs.getDate("FECHA_SALIDA").toLocalDate() : null);
        c.setHoraSalida(rs.getTime("HORA_SALIDA") != null ? 
            rs.getTime("HORA_SALIDA").toLocalTime() : null);
        c.setDevolucion(rs.getBigDecimal("DEVOLUCION"));
        return c;
    };

    public List<CheckInOut> listar() {
        String sql = "SELECT * FROM V_CHECKINOUT_DETALLE";
        return jdbcTemplate.query(sql, rowMapper);
    }

   public CheckInOut obtenerPorId(Long idCheck) {
    String sql = "SELECT * FROM V_CHECKINOUT_DETALLE WHERE ID_CHECK = ?";

    try {
        return jdbcTemplate.queryForObject(sql, rowMapper, idCheck);
    } catch (Exception e) {
        System.err.println("Error en obtenerPorId desde V_CHECKINOUT_DETALLE: " + e.getMessage());
        return null;
    }
}

    public String agregar(CheckInOut c) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall(
                "{call PKG_CHECK_IN_OUT_FRONT.SP_INSERT_CHECK(?,?,?,?,?,?,?)}")) {
                
                cs.setLong(1, c.getIdReserva());
                
                if (c.getFechaEntrada() != null) {
                    cs.setDate(2, java.sql.Date.valueOf(c.getFechaEntrada()));
                } else {
                    cs.setNull(2, Types.DATE);
                }
                
                if (c.getHoraEntrada() != null) {
                    cs.setTime(3, java.sql.Time.valueOf(c.getHoraEntrada()));
                } else {
                    cs.setNull(3, Types.TIME);
                }
                
                if (c.getFechaSalida() != null) {
                    cs.setDate(4, java.sql.Date.valueOf(c.getFechaSalida()));
                } else {
                    cs.setNull(4, Types.DATE);
                }
                
                if (c.getHoraSalida() != null) {
                    cs.setTime(5, java.sql.Time.valueOf(c.getHoraSalida()));
                } else {
                    cs.setNull(5, Types.TIME);
                }
                
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
                
                if (c.getFechaSalida() != null) {
                    cs.setDate(5, java.sql.Date.valueOf(c.getFechaSalida()));
                } else {
                    cs.setNull(5, Types.DATE);
                }
                
                if (c.getHoraSalida() != null) {
                    cs.setTime(6, java.sql.Time.valueOf(c.getHoraSalida()));
                } else {
                    cs.setNull(6, Types.TIME);
                }
                
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