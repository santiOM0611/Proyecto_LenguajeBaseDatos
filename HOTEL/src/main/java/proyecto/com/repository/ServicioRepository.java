/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.repository;

import proyecto.com.model.Servicio;
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
public class ServicioRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Servicio> rowMapper = (rs, rowNum) -> {
        Servicio s = new Servicio();
        s.setId(rs.getLong("ID_SERVICIO"));
        s.setIdHotel(rs.getLong("ID_HOTEL"));
        s.setNombre(rs.getString("NOMBRE_SERVICIO"));
        s.setDescripcion(rs.getString("DESCRIPCION_SERVICIO"));
        s.setCosto(rs.getBigDecimal("COSTO_SERVICIO"));
        s.setRutaImagen(rs.getString("RUTA_IMAGEN"));
        return s;
    };

    public List<Servicio> listar() {
        String sql = "SELECT * FROM V_Servicios_Detalle";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Servicio obtenerPorId(Long idServicio) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{? = call FN_GET_SERVICIO(?)}")) {
                cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
                cs.setLong(2, idServicio);
                cs.execute();

                try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                    if (rs != null && rs.next()) {
                        Servicio s = new Servicio();
                        s.setId(rs.getLong("ID_SERVICIO"));
                        s.setIdHotel(rs.getLong("ID_HOTEL"));
                        s.setNombre(rs.getString("NOMBRE_SERVICIO"));
                        s.setDescripcion(rs.getString("DESCRIPCION_SERVICIO"));
                        s.setCosto(rs.getBigDecimal("COSTO_SERVICIO"));
                        s.setRutaImagen(rs.getString("RUTA_IMAGEN"));
                        return s;
                    }
                }
            }
            return null;
        });
    }

    public String agregar(Servicio s) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{call PKG_SERVICIOS_FRONT.SP_AGREGAR_SERVICIO(?,?,?,?,?,?)}")) {
                cs.setLong(1, s.getIdHotel());
                cs.setString(2, s.getNombre());
                cs.setString(3, s.getDescripcion());
                cs.setBigDecimal(4, s.getCosto());
                cs.setString(5, s.getRutaImagen());
                cs.registerOutParameter(6, Types.VARCHAR); 

                cs.execute();
                String resultado = cs.getString(6);

                System.out.println("Resultado del SP_AGREGAR_SERVICIO: " + resultado);
                return resultado;
            } catch (Exception e) {
                System.err.println("Error en agregar servicio: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        });
    }

    public String editar(Servicio s) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{call PKG_SERVICIOS_FRONT.SP_EDITAR_SERVICIO(?,?,?,?,?,?,?)}")) {
                cs.setLong(1, s.getId());
                cs.setLong(2, s.getIdHotel());
                cs.setString(3, s.getNombre());
                cs.setString(4, s.getDescripcion());
                cs.setBigDecimal(5, s.getCosto());
                cs.setString(6, s.getRutaImagen());
                cs.registerOutParameter(7, Types.VARCHAR);

                cs.execute();
                String resultado = cs.getString(7);

                System.out.println("Resultado del SP_EDITAR_SERVICIO: " + resultado);
                return resultado;
            } catch (Exception e) {
                System.err.println("Error en editar servicio: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        });
    }

    public String eliminar(Long idServicio) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{call PKG_SERVICIOS_FRONT.SP_ELIMINAR_SERVICIO(?,?)}")) {
                cs.setLong(1, idServicio);
                cs.registerOutParameter(2, Types.VARCHAR);

                cs.execute();
                String resultado = cs.getString(2);

                System.out.println("Resultado del SP_ELIMINAR_SERVICIO: " + resultado);
                return resultado;
            } catch (Exception e) {
                System.err.println("Error en eliminar servicio: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        });
    }
}
