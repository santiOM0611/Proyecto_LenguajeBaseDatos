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
        return facturaRepository.findAll();
    }

    public Factura buscarPorId(Integer id) {
        return facturaRepository.findById(id).orElse(null);
    }

    public void guardar(Factura factura) {
        facturaRepository.save(factura);
    }

    public void eliminar(Integer id) {
        facturaRepository.deleteById(id);
    }
}
