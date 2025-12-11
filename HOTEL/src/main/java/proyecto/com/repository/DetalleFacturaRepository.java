package proyecto.com.repository;

import proyecto.com.model.DetalleFactura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.List;

@Repository
public class DetalleFacturaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<DetalleFactura> rowMapper = (rs, rowNum) -> {
        DetalleFactura df = new DetalleFactura();
        df.setId(rs.getLong("ID_DETALLEFACTURA"));
        df.setIdFactura(rs.getLong("ID_FACTURA"));
        df.setIdTipoHabitacion(rs.getLong("ID_TIPO_HABITACION"));
        df.setIdServicios(rs.getLong("ID_SERVICIOS"));
        df.setMontoTotal(rs.getBigDecimal("MONTO_TOTAL"));
        return df;
    };

    public List<DetalleFactura> listar() {
        String sql = "SELECT * FROM V_DetallesFactura_Detalle";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public DetalleFactura obtenerPorId(Long idDetalleFactura) {
        String sql = "SELECT * FROM V_DetallesFactura_Detalle WHERE ID_DETALLEFACTURA = ?";
        
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, idDetalleFactura);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            System.out.println("No se encontró detalle factura con ID: " + idDetalleFactura);
            return null;
        } catch (Exception e) {
            System.err.println("Error al obtener detalle factura por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public String agregar(DetalleFactura df) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{call PKG_DETALLES_FACTURA_FRONT.SP_AGREGAR_DETALLE_FACTURA(?,?,?,?,?)}")) {
                cs.setLong(1, df.getIdFactura());
                cs.setLong(2, df.getIdTipoHabitacion());
                cs.setLong(3, df.getIdServicios());
                cs.setBigDecimal(4, df.getMontoTotal());
                cs.registerOutParameter(5, Types.VARCHAR);

                cs.execute();
                String resultado = cs.getString(5);

                System.out.println("Resultado del SP_AGREGAR_DETALLE_FACTURA: " + resultado);
                return resultado;
            } catch (Exception e) {
                System.err.println("Error en agregar detalle factura: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        });
    }

    public String editar(DetalleFactura df) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{call PKG_DETALLES_FACTURA_FRONT.SP_EDITAR_DETALLE_FACTURA(?,?,?,?,?,?)}")) {
                cs.setLong(1, df.getId());
                cs.setLong(2, df.getIdFactura());
                cs.setLong(3, df.getIdTipoHabitacion());
                cs.setLong(4, df.getIdServicios());
                cs.setBigDecimal(5, df.getMontoTotal());
                cs.registerOutParameter(6, Types.VARCHAR);

                cs.execute();
                String resultado = cs.getString(6);

                System.out.println("Resultado del SP_EDITAR_DETALLE_FACTURA: " + resultado);
                return resultado;
            } catch (Exception e) {
                System.err.println("Error en editar detalle factura: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        });
    }

    public String eliminar(Long idDetalleFactura) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{call PKG_DETALLES_FACTURA_FRONT.SP_ELIMINAR_DETALLE_FACTURA(?,?)}")) {
                cs.setLong(1, idDetalleFactura);
                cs.registerOutParameter(2, Types.VARCHAR);

                cs.execute();
                String resultado = cs.getString(2);

                System.out.println("Resultado del SP_ELIMINAR_DETALLE_FACTURA: " + resultado);
                return resultado;
            } catch (Exception e) {
                System.err.println("Error en eliminar detalle factura: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        });
    }
}