/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.com.model.DetalleFactura;
import proyecto.com.repository.DetalleFacturaRepository;

import java.util.List;

@Service
public class DetalleFacturaService {

    @Autowired
    private DetalleFacturaRepository detalleFacturaRepository;

    public List<DetalleFactura> listarTodos() {
        return detalleFacturaRepository.listarTodos();
    }

    public DetalleFactura buscarPorId(int idDetalle) {
        return detalleFacturaRepository.buscarPorId(idDetalle);
    }

    public String guardar(DetalleFactura detalle) {
        return detalleFacturaRepository.insertar(detalle);
    }

    public String actualizar(DetalleFactura detalle) {
        return detalleFacturaRepository.actualizar(detalle);
    }

    public String eliminar(int idDetalle) {
        return detalleFacturaRepository.eliminar(idDetalle);
    }
}
