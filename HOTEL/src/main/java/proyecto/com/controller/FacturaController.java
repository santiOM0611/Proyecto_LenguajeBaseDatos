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

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Integer id, Model model) {
        model.addAttribute("factura", service.buscarPorId(id));
        return "facturas/ver";
    }

    // FORM AGREGAR NORMAL
    @GetMapping("/agregar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("factura", new Factura());
        return "facturas/agregar";
    }

    // FORM AGREGAR DESDE RESERVAS
    @GetMapping("/agregar/{idReserva}")
    public String agregarDesdeReserva(@PathVariable Integer idReserva, Model model) {
        Factura f = new Factura();
        f.setIdReserva(idReserva);
        f.setFecha(LocalDate.now());
        model.addAttribute("factura", f);
        return "facturas/agregar";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Factura factura, RedirectAttributes ra) {
        service.guardar(factura);
        ra.addFlashAttribute("msg", "Factura creada correctamente");
        return "redirect:/facturas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("factura", service.buscarPorId(id));
        return "facturas/modifica";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Factura factura, RedirectAttributes ra) {
        service.guardar(factura);
        ra.addFlashAttribute("msg", "Factura actualizada correctamente");
        return "redirect:/facturas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes ra) {
        service.eliminar(id);
        ra.addFlashAttribute("msg", "Factura eliminada correctamente");
        return "redirect:/facturas";
    }
}
