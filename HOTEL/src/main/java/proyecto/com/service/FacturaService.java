/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.com.model.Factura;
import proyecto.com.repository.FacturaRepository;

import java.util.List;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    public List<Factura> listar() {
        return facturaRepository.listar(); 
    }

    public Factura buscarPorId(int idFactura) {
        return facturaRepository.buscarPorId(idFactura);
    }

    public String guardar(Factura factura) {
        return facturaRepository.insertar(factura); 
    }

    public String actualizar(Factura factura) {
        return facturaRepository.actualizar(factura); 
    }

    public String eliminar(int idFactura) {
        return facturaRepository.eliminar(idFactura); 
    }
}
