package proyecto.com.repository;

import proyecto.com.model.Cliente;
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
public class ClienteRepositoryImpl implements ClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper para Cliente
    private Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cliente c = new Cliente();
        c.setCedula(rs.getLong("CEDULA"));
        c.setNombreCliente(rs.getString("NOMBRE_CLIENTE"));
        c.setPrimerApellido(rs.getString("PRIMER_APELLIDO"));
        c.setSegundoApellido(rs.getString("SEGUNDO_APELLIDO"));
        c.setTelefonoCliente(rs.getString("TELEFONO_CLIENTE"));
        c.setCorreoCliente(rs.getString("CORREO_CLIENTE"));
        return c;
    }

    @Override
    public List<Cliente> listar() {
        String sql = "SELECT * FROM V_CLIENTES_DETALLE ORDER BY NOMBRE_CLIENTE";
        return jdbcTemplate.query(sql, this::mapRow);
    }

    @Override
    public Cliente obtenerPorId(Long cedula) {
        String sql = "SELECT * FROM V_CLIENTES_DETALLE WHERE CEDULA = ?";
        List<Cliente> lista = jdbcTemplate.query(sql, new Object[]{cedula}, this::mapRow);
        return lista.isEmpty() ? null : lista.get(0);
    }

    @Override
    public String insertar(Cliente cliente) {
        try {
            SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName("PKG_CLIENTES_FRONT")
                    .withProcedureName("SP_INSERT_CLIENTE")
                    .declareParameters(
                            new SqlParameter("P_CEDULA", Types.NUMERIC),
                            new SqlParameter("P_NOMBRE", Types.VARCHAR),
                            new SqlParameter("P_APELLIDO1", Types.VARCHAR),
                            new SqlParameter("P_APELLIDO2", Types.VARCHAR),
                            new SqlParameter("P_TELEFONO", Types.VARCHAR),
                            new SqlParameter("P_CORREO", Types.VARCHAR),
                            new SqlOutParameter("P_MENSAJE", Types.VARCHAR)
                    );

            Map<String, Object> out = call.execute(
                    cliente.getCedula(),
                    cliente.getNombreCliente(),
                    cliente.getPrimerApellido(),
                    cliente.getSegundoApellido(),
                    cliente.getTelefonoCliente(),
                    cliente.getCorreoCliente()
            );

            return out.get("P_MENSAJE").toString();

        } catch (Exception e) {
            return "Error al insertar cliente: " + e.getMessage();
        }
    }

    @Override
    public String actualizar(Cliente cliente) {
        try {
            SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName("PKG_CLIENTES_FRONT")
                    .withProcedureName("SP_UPDATE_CLIENTE")
                    .declareParameters(
                            new SqlParameter("P_CEDULA", Types.NUMERIC),
                            new SqlParameter("P_NOMBRE", Types.VARCHAR),
                            new SqlParameter("P_APELLIDO1", Types.VARCHAR),
                            new SqlParameter("P_APELLIDO2", Types.VARCHAR),
                            new SqlParameter("P_TELEFONO", Types.VARCHAR),
                            new SqlParameter("P_CORREO", Types.VARCHAR),
                            new SqlOutParameter("P_MENSAJE", Types.VARCHAR)
                    );

            Map<String, Object> out = call.execute(
                    cliente.getCedula(),
                    cliente.getNombreCliente(),
                    cliente.getPrimerApellido(),
                    cliente.getSegundoApellido(),
                    cliente.getTelefonoCliente(),
                    cliente.getCorreoCliente()
            );

            return out.get("P_MENSAJE").toString();

        } catch (Exception e) {
            return "Error al actualizar cliente: " + e.getMessage();
        }
    }

    @Override
    public String eliminar(Long cedula) {
        try {
            SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName("PKG_CLIENTES_FRONT")
                    .withProcedureName("SP_DELETE_CLIENTE")
                    .declareParameters(
                            new SqlParameter("P_CEDULA", Types.NUMERIC),
                            new SqlOutParameter("P_MENSAJE", Types.VARCHAR)
                    );

            Map<String, Object> out = call.execute(cedula);

            return out.get("P_MENSAJE").toString();

        } catch (Exception e) {
            return "Error al eliminar cliente: " + e.getMessage();
        }
    }

    @Override
    public Cliente buscarPorIdConFuncion(Long cedula) {
        try {
            SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                    .withFunctionName("FN_GET_CLIENTE")
                    .declareParameters(
                            new SqlParameter("P_CEDULA", Types.NUMERIC)
                    );

            Map<String, Object> out = call.execute(cedula);

            // Como devuelve un REF CURSOR, lo mapeamos manualmente
            List<Cliente> lista = jdbcTemplate.query(
                    "SELECT * FROM TABLE(FN_GET_CLIENTE(?))", 
                    new Object[]{cedula}, this::mapRow
            );

            return lista.isEmpty() ? null : lista.get(0);

        } catch (Exception e) {
            return null;
        }
    }
}
