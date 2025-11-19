/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController  
public class testController {
    
     @Autowired
    private DataSource dataSource;

    @GetMapping("/probarConexion")
    public String probarConexion() {
        try (Connection conn = dataSource.getConnection()) {
            return "✔ Conexión a Oracle EXITOSA!";
        } catch (Exception e) {
            return "❌ ERROR de conexión: " + e.getMessage();
        }
    }
    
}
