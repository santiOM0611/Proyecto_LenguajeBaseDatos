package proyecto.com.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import proyecto.com.model.DetalleFactura;

import java.sql.*;
import java.util.List;

@Repository
public class DetalleFacturaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<DetalleFactura> rowMapper = (rs, rowNum) -> {
        DetalleFactura d = new DetalleFactura();
        d.setIdDetalleFactura(rs.getInt("ID_DETALLEFACTURA"));
        d.setIdFactura(rs.getInt("ID_FACTURA"));
        d.setIdTipoHabitacion(rs.getInt("ID_TIPO_HABITACION"));
        d.setIdServicios(rs.getInt("ID_SERVICIOS"));
        if (rs.getBigDecimal("MONTO_TOTAL") != null) {
            d.setMontoTotal(rs.getBigDecimal("MONTO_TOTAL"));
        }
        return d;
    };

    public List<DetalleFactura> listarTodos() {
        String sql = "SELECT * FROM V_Detalle_Factura";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public DetalleFactura buscarPorId(int idDetalle) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{? = call FN_GET_DETALLE_FACTURA(?)}")) {

                cs.registerOutParameter(1, Types.REF_CURSOR);
                cs.setInt(2, idDetalle);
                cs.execute();

                ResultSet rs = (ResultSet) cs.getObject(1);

                if (rs != null && rs.next()) {
                    DetalleFactura d = new DetalleFactura();
                    d.setIdDetalleFactura(rs.getInt("ID_DETALLEFACTURA"));
                    d.setIdFactura(rs.getInt("ID_FACTURA"));
                    d.setIdTipoHabitacion(rs.getInt("ID_TIPO_HABITACION"));
                    d.setIdServicios(rs.getInt("ID_SERVICIOS"));
                    if (rs.getBigDecimal("MONTO_TOTAL") != null) {
                        d.setMontoTotal(rs.getBigDecimal("MONTO_TOTAL"));
                    }
                    return d;
                }
                return null;
            }
        });
    }

    public String insertar(DetalleFactura d) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{call PKG_DETALLE_FACTURA_FRONT.SP_AGREGAR_DETALLE(?, ?, ?, ?, ?)}")) {

                cs.setInt(1, d.getIdFactura());
                cs.setInt(2, d.getIdTipoHabitacion());
                cs.setInt(3, d.getIdServicios());
                cs.setBigDecimal(4, d.getMontoTotal());
                cs.registerOutParameter(5, Types.VARCHAR);

                cs.execute();
                return cs.getString(5);
            }
        });
    }

    public String actualizar(DetalleFactura d) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{call PKG_DETALLE_FACTURA_FRONT.SP_EDITAR_DETALLE(?, ?, ?, ?, ?, ?)}")) {

                cs.setInt(1, d.getIdDetalleFactura());
                cs.setInt(2, d.getIdFactura());
                cs.setInt(3, d.getIdTipoHabitacion());
                cs.setInt(4, d.getIdServicios());
                cs.setBigDecimal(5, d.getMontoTotal());
                cs.registerOutParameter(6, Types.VARCHAR);

                cs.execute();
                return cs.getString(6);
            }
        });
    }

    public String eliminar(int idDetalle) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{call PKG_DETALLE_FACTURA_FRONT.SP_ELIMINAR_DETALLE(?, ?)}")) {

                cs.setInt(1, idDetalle);
                cs.registerOutParameter(2, Types.VARCHAR);

                cs.execute();
                return cs.getString(2);
            }
        });
    }
}
