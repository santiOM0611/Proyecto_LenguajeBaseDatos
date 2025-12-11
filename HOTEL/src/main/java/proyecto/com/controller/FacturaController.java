/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.controller;

import proyecto.com.model.Factura;
import proyecto.com.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("facturas", service.listar());
        return "facturas/facturas";
    }


    @GetMapping("/buscarID")
    public String mostrarFormularioBuscar(Model model) {
        model.addAttribute("factura", new Factura()); 
        model.addAttribute("encontrado", false);
        return "facturas/buscarID";
    }

    @PostMapping("/buscarID")
    public String buscarPorId(@RequestParam("idFactura") int idFactura, Model model) {
        Factura factura = service.buscarPorId(idFactura);
        if (factura != null) {
            model.addAttribute("encontrado", true);
            model.addAttribute("factura", factura);
        } else {
            model.addAttribute("encontrado", false);
            model.addAttribute("error", "No se encontró la factura con ID " + idFactura);
            model.addAttribute("factura", new Factura()); 
        }
        return "facturas/buscarID";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable int id, Model model) {
        Factura factura = service.buscarPorId(id);
        if (factura == null) {
            model.addAttribute("error", "No se encontró la factura con ID " + id);
            return "facturas/errorFactura"; 
        }
        model.addAttribute("factura", factura);
        return "facturas/ver";
    }

    @GetMapping("/agregar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("factura", new Factura());
        return "facturas/agregar";
    }

    @GetMapping("/agregar/{idReserva}")
    public String agregarDesdeReserva(@PathVariable int idReserva, Model model) {
        Factura f = new Factura();
        f.setIdReserva(idReserva);
        f.setFecha(LocalDate.now());
        model.addAttribute("factura", f);
        return "facturas/agregar";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Factura factura, RedirectAttributes ra) {
        String msg = service.guardar(factura); 
        ra.addFlashAttribute("msg", msg);
        return "redirect:/facturas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model) {
        Factura factura = service.buscarPorId(id);
        if (factura == null) {
            model.addAttribute("error", "No se encontró la factura con ID " + id);
            return "facturas/errorFactura"; 
        }
        model.addAttribute("factura", factura);
        return "facturas/modifica";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Factura factura, RedirectAttributes ra) {
        String msg = service.actualizar(factura); 
        ra.addFlashAttribute("msg", msg);
        return "redirect:/facturas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes ra) {
        String msg = service.eliminar(id); 
        ra.addFlashAttribute("msg", msg);
        return "redirect:/facturas";
    }
}
