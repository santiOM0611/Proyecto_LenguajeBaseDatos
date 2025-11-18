package proyecto.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
    private static final String USER = "HOTEL_SELVAS_DEL_HIMALAYA"; 
    private static final String PASSWORD = "123";         

    private Connection conexion;

    public Conexion() {
        this.conexion = null;
    }

    public Connection conectar() {
        try {
            Class.forName(DRIVER); // Cargar el driver JDBC
            this.conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a Oracle!");
        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró el driver JDBC de Oracle: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("ERROR DE CONEXIÓN: " + e.getMessage());
        }
        return this.conexion;
    }

    public void desconectar() {
        try {
            if (this.conexion != null) {
                this.conexion.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR AL DESCONECTAR: " + e.getMessage());
        }
    }
    
}
