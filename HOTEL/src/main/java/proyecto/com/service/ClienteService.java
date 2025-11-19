package proyecto.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import proyecto.com.model.Cliente;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private JdbcTemplate jdbc;

    // LISTAR
    public List<Cliente> listar() {
        String sql = "SELECT CEDULA, NOMBRE_CLIENTE, PRIMER_APELLIDO, SEGUNDO_APELLIDO FROM CLIENTES";

        return jdbc.query(sql, (rs, rowNum) -> {
            Cliente c = new Cliente();
            c.setCedula(rs.getString("CEDULA"));
            c.setNombreCliente(rs.getString("NOMBRE_CLIENTE"));
            c.setPrimerApellido(rs.getString("PRIMER_APELLIDO"));
            c.setSegundoApellido(rs.getString("SEGUNDO_APELLIDO"));
            return c;
        });
    }

    // BUSCAR POR CEDULA
    public Cliente buscar(String cedula) {
        String sql = "SELECT CEDULA, NOMBRE_CLIENTE, PRIMER_APELLIDO, SEGUNDO_APELLIDO FROM CLIENTES WHERE CEDULA = ?";

        return jdbc.queryForObject(sql, new Object[]{cedula}, (rs, rowNum) -> {
            Cliente c = new Cliente();
            c.setCedula(rs.getString("CEDULA"));
            c.setNombreCliente(rs.getString("NOMBRE_CLIENTE"));
            c.setPrimerApellido(rs.getString("PRIMER_APELLIDO"));
            c.setSegundoApellido(rs.getString("SEGUNDO_APELLIDO"));
            return c;
        });
    }

    // GUARDAR
    public void guardar(Cliente c) {
        String sql = "INSERT INTO CLIENTES (CEDULA, NOMBRE_CLIENTE, PRIMER_APELLIDO, SEGUNDO_APELLIDO) VALUES (?, ?, ?, ?)";

        jdbc.update(sql,
                c.getCedula(),
                c.getNombreCliente(),
                c.getPrimerApellido(),
                c.getSegundoApellido()
        );
    }

    // ACTUALIZAR
    public void actualizar(Cliente c) {
        String sql = "UPDATE CLIENTES SET NOMBRE_CLIENTE = ?, PRIMER_APELLIDO = ?, SEGUNDO_APELLIDO = ? WHERE CEDULA = ?";

        jdbc.update(sql,
                c.getNombreCliente(),
                c.getPrimerApellido(),
                c.getSegundoApellido(),
                c.getCedula()
        );
    }

    // ELIMINAR
    public void eliminar(String cedula) {
        String sql = "DELETE FROM CLIENTES WHERE CEDULA = ?";
        jdbc.update(sql, cedula);
    }
}
