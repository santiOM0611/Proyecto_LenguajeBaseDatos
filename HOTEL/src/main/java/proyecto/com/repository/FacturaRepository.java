/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.com.model.Factura;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {

}

