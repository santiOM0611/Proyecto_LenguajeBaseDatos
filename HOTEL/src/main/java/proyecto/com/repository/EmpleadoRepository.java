package proyecto.com.repository;

import proyecto.com.model.Empleado;
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
public class EmpleadoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Empleado> rowMapper = (rs, rowNum) -> {
        Empleado e = new Empleado();
        e.setIdEmpleado(rs.getInt("ID_EMPLEADO"));
        e.setIdHotel(rs.getInt("ID_HOTEL"));
        e.setIdRol(rs.getInt("ID_ROL"));
        e.setNombre(rs.getString("NOMBRE"));
        e.setApellido1(rs.getString("PRIMER_APELLIDO"));
        e.setApellido2(rs.getString("SEGUNDO_APELLIDO"));
        e.setTelefono(rs.getString("TELEFONO_EMPLEADO"));
        e.setCorreo(rs.getString("CORREO_EMPLEADO"));
        return e;
    };

    public List<Empleado> listar() {
        String sql = "SELECT * FROM V_Empleados_Detalle";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Empleado obtenerPorId(int idEmpleado) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{ ? = call FN_GET_EMPLEADO(?) }")) {
                cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
                cs.setInt(2, idEmpleado);
                cs.execute();

                try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                    if (rs.next()) {
                        Empleado e = new Empleado();
                        e.setIdEmpleado(rs.getInt("ID_EMPLEADO"));
                        e.setIdHotel(rs.getInt("ID_HOTEL"));
                        e.setIdRol(rs.getInt("ID_ROL"));
                        e.setNombre(rs.getString("NOMBRE"));
                        e.setApellido1(rs.getString("APELLIDO1"));
                        e.setApellido2(rs.getString("APELLIDO2"));
                        return e;
                    } else {
                        return null;
                    }
                }
            }
        });
    }

    public String insertar(Empleado e) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall(
                    "{call PKG_EMPLEADOS_FRONT.SP_INSERT_EMPLEADO(?, ?, ?, ?, ?, ?, ?, ?)}")) {
                cs.setInt(1, e.getIdHotel());   
                cs.setInt(2, e.getIdRol());     
                cs.setString(3, e.getNombre());
                cs.setString(4, e.getApellido1());
                cs.setString(5, e.getApellido2());
                cs.setString(6, e.getTelefono());
                cs.setString(7, e.getCorreo());
                cs.registerOutParameter(8, Types.VARCHAR);
                cs.execute();
                return cs.getString(8);
            }
        });
    }

    public String actualizar(Empleado e) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall(
                    "{call PKG_EMPLEADOS_FRONT.SP_UPDATE_EMPLEADO(?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

                cs.setInt(1, e.getIdEmpleado());
                cs.setInt(2, e.getIdHotel());
                cs.setInt(3, e.getIdRol());
                cs.setString(4, e.getNombre());
                cs.setString(5, e.getApellido1());
                cs.setString(6, e.getApellido2());
                cs.setString(7, e.getTelefono());
                cs.setString(8, e.getCorreo());
                cs.registerOutParameter(9, Types.VARCHAR);

                cs.execute();
                return cs.getString(9);
            }
        });
    }

    public String eliminar(int idEmpleado) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{call PKG_EMPLEADOS_FRONT.SP_DELETE_EMPLEADO(?, ?)}")) {
                cs.setInt(1, idEmpleado);
                cs.registerOutParameter(2, Types.VARCHAR);
                cs.execute();
                return cs.getString(2);
            }
        });
    }
}
