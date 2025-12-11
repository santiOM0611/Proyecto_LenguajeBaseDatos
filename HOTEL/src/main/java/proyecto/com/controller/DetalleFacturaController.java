package proyecto.com.controller;

import proyecto.com.model.DetalleFactura;
import proyecto.com.service.DetalleFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/detallesFactura")
public class DetalleFacturaController {
    
    @Autowired
    private DetalleFacturaService service;
    
    @GetMapping("/listado")
    public String listadoDetallesFactura(Model model) {
        model.addAttribute("detalleFactura", service.listarTodos());
        return "detallesFactura/listado";
    }
    
    @GetMapping("/agregar")
    public String nuevoDetalleFactura(Model model) {
        model.addAttribute("detalleFactura", new DetalleFactura());
        return "detallesFactura/agregar";
    }
    
    @PostMapping("/guardar")
    public String guardarDetalleFactura(@ModelAttribute DetalleFactura detalleFactura, Model model) {
        try {
            service.guardar(detalleFactura);
            return "redirect:/detallesFactura/listado";
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            model.addAttribute("errorMensaje", "Error de integridad: Verifique IDs de Factura, Tipo Habitación y Servicios");
            model.addAttribute("detalleFactura", detalleFactura);
            return "detallesFactura/agregar";
        } catch (Exception e) {
            model.addAttribute("errorMensaje", "Error al guardar: " + e.getMessage());
            model.addAttribute("detalleFactura", detalleFactura);
            return "detallesFactura/agregar";
        }
    }
    
    @GetMapping("/editar/{id}")
    public String editarDetalleFactura(@PathVariable Long id, Model model) {
        Optional<DetalleFactura> detalleFactura = service.obtenerPorId(id);
        if (detalleFactura.isPresent()) {
            model.addAttribute("detalleFactura", detalleFactura.get());
            return "detallesFactura/modifica";
        } else {
            model.addAttribute("errorMensaje", "No se encontró el detalle factura con ID " + id);
            return "redirect:/detallesFactura/listado";
        }
    }
    
    @GetMapping("/ver/{id}")
    public String verDetalleFactura(@PathVariable Long id, Model model) {
        Optional<DetalleFactura> detalleFactura = service.obtenerPorId(id);
        if (detalleFactura.isPresent()) {
            model.addAttribute("detalleFactura", detalleFactura.get());
            return "detallesFactura/ver";
        } else {
            model.addAttribute("errorMensaje", "No se encontró el detalle factura con ID " + id);
            return "redirect:/detallesFactura/listado";
        }
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarDetalleFactura(@PathVariable Long id, Model model) {
        Optional<DetalleFactura> detalleFactura = service.obtenerPorId(id);
        if (detalleFactura.isPresent()) {
            service.eliminar(id);
        } else {
            model.addAttribute("errorMensaje", "No se encontró el detalle factura con ID " + id);
        }
        return "redirect:/detallesFactura/listado";
    }
    
    @GetMapping("/buscar")
    public String mostrarFormularioBusqueda() {
        return "detallesFactura/buscar";
    }
    
    @PostMapping("/buscar")
    public String buscar(@RequestParam("idDetalleFactura") Long idDetalleFactura, Model model) {
        DetalleFactura detalleFactura = service.buscarPorId(idDetalleFactura);
        if (detalleFactura == null) {
            model.addAttribute("encontrado", false);
            model.addAttribute("mensaje", "No se encontró el detalle factura con ID " + idDetalleFactura);
        } else {
            model.addAttribute("encontrado", true);
            model.addAttribute("detalleFactura", detalleFactura);
        }
        return "detallesFactura/buscar";
    }
}
