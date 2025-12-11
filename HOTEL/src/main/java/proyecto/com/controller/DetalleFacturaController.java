/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.controller;

import proyecto.com.model.DetalleFactura;
import proyecto.com.service.DetalleFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/detalleFacturas")
public class DetalleFacturaController {

    @Autowired
    private DetalleFacturaService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("detalles", service.listarTodos());
        return "detalleFacturas/detalleFacturas";
    }

    @GetMapping("/buscarID")
    public String mostrarFormularioBuscar(Model model) {
        model.addAttribute("detalle", new DetalleFactura());
        model.addAttribute("encontrado", false);
        return "detalleFacturas/buscarID";
    }

    @PostMapping("/buscarID")
    public String buscarPorId(@RequestParam("idDetalle") int idDetalle, Model model) {
        DetalleFactura detalle = service.buscarPorId(idDetalle);
        if (detalle != null) {
            model.addAttribute("encontrado", true);
            model.addAttribute("detalle", detalle);
        } else {
            model.addAttribute("encontrado", false);
            model.addAttribute("error", "No se encontró el detalle con ID " + idDetalle);
            model.addAttribute("detalle", new DetalleFactura());
        }
        return "detalleFacturas/buscarID";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable int id, Model model) {
        DetalleFactura detalle = service.buscarPorId(id);
        if (detalle == null) {
            model.addAttribute("error", "No se encontró el detalle con ID " + id);
            return "detalleFacturas/errorDetalle"; 
        }
        model.addAttribute("detalle", detalle);
        return "detalleFacturas/ver";
    }


    @GetMapping("/agregar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("detalle", new DetalleFactura());
        return "detalleFacturas/agregar";
    }


    @GetMapping("/agregar/factura/{idFactura}")
    public String agregarDesdeFactura(@PathVariable int idFactura, Model model) {
        DetalleFactura d = new DetalleFactura();
        d.setIdFactura(idFactura);
        model.addAttribute("detalle", d);
        return "detalleFacturas/agregar";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute DetalleFactura detalle, RedirectAttributes ra) {
        String msg = service.guardar(detalle);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/detalleFacturas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model) {
        DetalleFactura detalle = service.buscarPorId(id);
        if (detalle == null) {
            model.addAttribute("error", "No se encontró el detalle con ID " + id);
            return "detalleFacturas/errorDetalle";
        }
        model.addAttribute("detalle", detalle);
        return "detalleFacturas/modifica";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute DetalleFactura detalle, RedirectAttributes ra) {
        String msg = service.actualizar(detalle);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/detalleFacturas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes ra) {
        String msg = service.eliminar(id);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/detalleFacturas";
    }
}
