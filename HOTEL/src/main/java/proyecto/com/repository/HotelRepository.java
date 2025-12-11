package proyecto.com.repository;

import proyecto.com.model.Hotel;
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
public class HotelRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Hotel> rowMapper = (rs, rowNum) -> {
        Hotel h = new Hotel();
        h.setIdHotel(rs.getInt("ID_HOTEL"));
        h.setNombreHotel(rs.getString("NOMBRE_HOTEL"));
        h.setTelefonoHotel(rs.getString("TELEFONO_HOTEL"));
        h.setCorreoHotel(rs.getString("CORREO_HOTEL"));
        h.setDireccionExacta(rs.getString("DIRECCION_EXACTA"));
        h.setRutaImagen(rs.getString("RUTA_IMAGEN"));
        h.setProvincia(rs.getString("NOMBRE_PROVINCIA"));
        h.setCanton(rs.getString("NOMBRE_CANTON"));
        h.setDistrito(rs.getString("NOMBRE_DISTRITO"));
        return h;
    };

    public List<Hotel> listar() {
        String sql = "SELECT * FROM V_HOTELES_DETALLE";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Hotel obtenerPorId(int id) {
        String sql = "SELECT * FROM V_HOTELES_DETALLE WHERE ID_HOTEL = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    // ✅ Insertar: recibe NOMBRES de provincia/cantón/distrito
    public String insertar(Hotel h) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall(
                    "{call PKG_HOTELES_FRONT.SP_INSERT_HOTEL(?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

                cs.setString(1, h.getNombreHotel());
                cs.setString(2, h.getTelefonoHotel());
                cs.setString(3, h.getCorreoHotel());
                cs.setString(4, h.getProvincia());
                cs.setString(5, h.getCanton());
                cs.setString(6, h.getDistrito());
                cs.setString(7, h.getDireccionExacta());
                cs.setString(8, h.getRutaImagen());
                cs.registerOutParameter(9, Types.VARCHAR);

                cs.execute();
                return cs.getString(9);
            }
        });
    }

    // ✅ Actualizar: recibe ID_HOTEL + NOMBRES
    public String actualizar(Hotel h) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall(
                    "{call PKG_HOTELES_FRONT.SP_UPDATE_HOTEL(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

                cs.setInt(1, h.getIdHotel());
                cs.setString(2, h.getNombreHotel());
                cs.setString(3, h.getTelefonoHotel());
                cs.setString(4, h.getCorreoHotel());
                cs.setString(5, h.getProvincia());
                cs.setString(6, h.getCanton());
                cs.setString(7, h.getDistrito());
                cs.setString(8, h.getDireccionExacta());
                cs.setString(9, h.getRutaImagen());
                cs.registerOutParameter(10, Types.VARCHAR);

                cs.execute();
                return cs.getString(10);
            }
        });
    }

    public String eliminar(int idHotel) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{call PKG_HOTELES_FRONT.SP_DELETE_HOTEL(?, ?)}")) {
                cs.setInt(1, idHotel);
                cs.registerOutParameter(2, Types.VARCHAR);
                cs.execute();
                return cs.getString(2);
            }
        });
    }
    public Hotel buscarPorIdConFuncion(int id) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{? = call FN_GET_HOTEL(?)}")) {
                cs.registerOutParameter(1, Types.REF_CURSOR);
                cs.setInt(2, id);
                cs.execute();

                ResultSet rs = (ResultSet) cs.getObject(1);
                if (rs != null && rs.next()) {
                    Hotel h = new Hotel();
                    h.setIdHotel(rs.getInt("ID_HOTEL"));
                    h.setNombreHotel(rs.getString("NOMBRE_HOTEL"));
                    h.setTelefonoHotel(rs.getString("TELEFONO_HOTEL"));
                    h.setCorreoHotel(rs.getString("CORREO_HOTEL"));
                    h.setDireccionExacta(rs.getString("DIRECCION_EXACTA"));
                    h.setRutaImagen(rs.getString("RUTA_IMAGEN"));
                    h.setProvincia(rs.getString("NOMBRE_PROVINCIA"));
                    h.setCanton(rs.getString("NOMBRE_CANTON"));
                    h.setDistrito(rs.getString("NOMBRE_DISTRITO"));
                    return h;
                }
                return null;
            }
        });
    }
}
