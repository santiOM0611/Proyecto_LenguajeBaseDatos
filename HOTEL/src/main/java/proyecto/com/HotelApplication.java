package proyecto.com;

import java.sql.Connection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelApplication.class, args);

        Conexion conexion = new Conexion();
        Connection conn = conexion.conectar();

        if (conn != null) {
            System.out.println("La conexión funciona correctamente desde Java.");
          
        } else {
            System.out.println("Error en la conexión.");
        }
    }
}
