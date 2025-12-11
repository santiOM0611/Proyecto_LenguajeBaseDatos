package proyecto.com.repository;

import proyecto.com.model.Empleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class EmpleadoRepositoryImpl implements EmpleadoRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private Empleado mapRow(ResultSet rs, int rowNum) throws SQLException {
        Empleado e = new Empleado();
        e.setIdEmpleado(rs.getInt("ID_EMPLEADO"));
        e.setIdRol(rs.getInt("ID_ROL"));
        e.setNombre(rs.getString("NOMBRE"));
        e.setApellido1(rs.getString("PRIMER_APELLIDO"));
        e.setApellido2(rs.getString("SEGUNDO_APELLIDO"));
        e.setTelefono(rs.getString("TELEFONO_EMPLEADO"));
        e.setCorreo(rs.getString("CORREO_EMPLEADO"));
        return e;
    }


    @Override
    public List<Empleado> listar() {
        String sql = "SELECT * FROM V_EMPLEADOS_DETALLE ORDER BY NOMBRE";
        return jdbcTemplate.query(sql, this::mapRow);
    }

 
    public Empleado obtenerPorId(Integer idEmpleado) {
        String sql = "SELECT * FROM V_EMPLEADOS_DETALLE WHERE ID_EMPLEADO = ?";
        List<Empleado> lista = jdbcTemplate.query(sql, new Object[]{idEmpleado}, this::mapRow);
        return lista.isEmpty() ? null : lista.get(0);
    }

   
    @Override
    public String insertar(Empleado empleado) {
        try {
            SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName("PKG_EMPLEADOS_FRONT")
                    .withProcedureName("SP_INSERT_EMPLEADO")
                    .declareParameters(
                            new SqlParameter("P_ID_EMPLEADO", Types.NUMERIC),
                            new SqlParameter("P_ID_ROL", Types.NUMERIC),
                            new SqlParameter("P_NOMBRE", Types.VARCHAR),
                            new SqlParameter("P_APELLIDO1", Types.VARCHAR),
                            new SqlParameter("P_APELLIDO2", Types.VARCHAR),
                            new SqlParameter("P_TELEFONO", Types.VARCHAR),
                            new SqlParameter("P_CORREO", Types.VARCHAR),
                            new SqlOutParameter("P_MENSAJE", Types.VARCHAR)
                    );

            Map<String, Object> out = call.execute(
                    empleado.getIdEmpleado(),
                    empleado.getIdRol(),
                    empleado.getNombre(),
                    empleado.getApellido1(),
                    empleado.getApellido2(),
                    empleado.getTelefono(),
                    empleado.getCorreo()
            );

            return out.get("P_MENSAJE").toString();

        } catch (Exception e) {
            return "Error al insertar empleado: " + e.getMessage();
        }
    }


    @Override
    public String actualizar(Empleado empleado) {
        try {
            SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName("PKG_EMPLEADOS_FRONT")
                    .withProcedureName("SP_UPDATE_EMPLEADO")
                    .declareParameters(
                            new SqlParameter("P_ID_EMPLEADO", Types.NUMERIC),
                            new SqlParameter("P_ID_ROL", Types.NUMERIC),
                            new SqlParameter("P_NOMBRE", Types.VARCHAR),
                            new SqlParameter("P_APELLIDO1", Types.VARCHAR),
                            new SqlParameter("P_APELLIDO2", Types.VARCHAR),
                            new SqlParameter("P_TELEFONO", Types.VARCHAR),
                            new SqlParameter("P_CORREO", Types.VARCHAR),
                            new SqlOutParameter("P_MENSAJE", Types.VARCHAR)
                    );

            Map<String, Object> out = call.execute(
                    empleado.getIdEmpleado(),
                    empleado.getIdRol(),
                    empleado.getNombre(),
                    empleado.getApellido1(),
                    empleado.getApellido2(),
                    empleado.getTelefono(),
                    empleado.getCorreo()
            );

            return out.get("P_MENSAJE").toString();

        } catch (Exception e) {
            return "Error al actualizar empleado: " + e.getMessage();
        }
    }

    public String eliminar(Integer idEmpleado) {
        try {
            SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName("PKG_EMPLEADOS_FRONT")
                    .withProcedureName("SP_DELETE_EMPLEADO")
                    .declareParameters(
                            new SqlParameter("P_ID_EMPLEADO", Types.NUMERIC),
                            new SqlOutParameter("P_MENSAJE", Types.VARCHAR)
                    );

            Map<String, Object> out = call.execute(idEmpleado);

            return out.get("P_MENSAJE").toString();

        } catch (Exception e) {
            return "Error al eliminar empleado: " + e.getMessage();
        }
    }


    public Empleado buscarPorIdConFuncion(Integer idEmpleado) {
        try {
            // Ejecuta la función
            SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                    .withFunctionName("FN_GET_EMPLEADO")
                    .declareParameters(
                            new SqlParameter("P_ID_EMPLEADO", Types.NUMERIC)
                    );

            call.execute(idEmpleado);

            // Obtener el REF CURSOR con un SELECT TABLE(...)
            List<Empleado> lista = jdbcTemplate.query(
                    "SELECT * FROM TABLE(FN_GET_EMPLEADO(?))",
                    new Object[]{idEmpleado},
                    this::mapRow
            );

            return lista.isEmpty() ? null : lista.get(0);

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Empleado obtenerPorId(Long cedula) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String eliminar(Long cedula) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Empleado buscarPorIdConFuncion(Long cedula) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
