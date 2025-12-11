/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import proyecto.com.model.Factura;

import java.sql.*;
import java.util.List;

@Repository
public class FacturaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Factura> rowMapper = (rs, rowNum) -> {
        Factura f = new Factura();
        f.setIdFactura(rs.getInt("ID_FACTURA"));
        f.setIdReserva(rs.getInt("ID_RESERVA"));
        if(rs.getDate("FECHA") != null) {
            f.setFecha(rs.getDate("FECHA").toLocalDate());
        }
        return f;
    };

    public List<Factura> listar() {
        String sql = "SELECT * FROM V_Facturas"; 
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Factura buscarPorId(int idFactura) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{? = call FN_GET_FACTURA(?)}")) {

                cs.registerOutParameter(1, Types.REF_CURSOR);
                cs.setInt(2, idFactura);
                cs.execute();

                ResultSet rs = (ResultSet) cs.getObject(1);

                if (rs != null && rs.next()) {
                    Factura f = new Factura();
                    f.setIdFactura(rs.getInt("ID_FACTURA"));
                    f.setIdReserva(rs.getInt("ID_RESERVA"));
                    if(rs.getDate("FECHA") != null) {
                        f.setFecha(rs.getDate("FECHA").toLocalDate());
                    }
                    return f;
                }
                return null;
            }
        });
    }

    public String insertar(Factura f) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{call PKG_FACTURAS_FRONT.SP_AGREGAR_FACTURA(?, ?, ?)}")) {

                cs.setInt(1, f.getIdReserva());
                cs.setDate(2, Date.valueOf(f.getFecha()));
                cs.registerOutParameter(3, Types.VARCHAR);

                cs.execute();
                return cs.getString(3);
            }
        });
    }

    public String actualizar(Factura f) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{call PKG_FACTURAS_FRONT.SP_EDITAR_FACTURA(?, ?, ?, ?)}")) {

                cs.setInt(1, f.getIdFactura());
                cs.setInt(2, f.getIdReserva());
                cs.setDate(3, Date.valueOf(f.getFecha()));
                cs.registerOutParameter(4, Types.VARCHAR);

                cs.execute();
                return cs.getString(4);
            }
        });
    }

    public String eliminar(int idFactura) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{call PKG_FACTURAS_FRONT.SP_ELIMINAR_FACTURA(?, ?)}")) {

                cs.setInt(1, idFactura);
                cs.registerOutParameter(2, Types.VARCHAR);

                cs.execute();
                return cs.getString(2);
            }
        });
    }
}
