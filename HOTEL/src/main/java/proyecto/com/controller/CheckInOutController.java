/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.controller;

import proyecto.com.model.CheckInOut;
import proyecto.com.service.CheckInOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/check")
public class CheckInOutController {
    
    @Autowired
    private CheckInOutService service;
    
    @GetMapping("/listado")
    public String listadoChecks(Model model) {
        model.addAttribute("checks", service.listarTodos());
        return "check/listado";
    }
    
    @GetMapping("/agregar")
    public String nuevoCheck(Model model) {
        model.addAttribute("check", new CheckInOut());
        return "check/agregar";
    }
    
    @PostMapping("/guardar")
    public String guardarCheck(@ModelAttribute CheckInOut check, Model model) {
        try {
            service.guardar(check);
            return "redirect:/check/listado";
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            model.addAttribute("errorMensaje", "La reserva con ID " + check.getIdReserva() + " no existe en el sistema");
            model.addAttribute("check", check);
            return "check/agregar";
        }
    }
    
    @GetMapping("/editar/{id}")
    public String editarCheck(@PathVariable Long id, Model model) {
        Optional<CheckInOut> check = service.obtenerPorId(id);
        if (check.isPresent()) {
            model.addAttribute("check", check.get());
            return "check/modifica";
        } else {
            model.addAttribute("errorMensaje", "No se encontró el check-in/out con ID " + id);
            return "redirect:/check/listado";
        }
    }
    
    @GetMapping("/ver/{id}")
    public String verCheck(@PathVariable Long id, Model model) {
        Optional<CheckInOut> check = service.obtenerPorId(id);
        if (check.isPresent()) {
            model.addAttribute("check", check.get());
            return "check/ver";
        } else {
            model.addAttribute("errorMensaje", "No se encontró el check-in/out con ID " + id);
            return "redirect:/check/listado";
        }
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarCheck(@PathVariable Long id, Model model) {
        Optional<CheckInOut> check = service.obtenerPorId(id);
        if (check.isPresent()) {
            service.eliminar(id);
        } else {
            model.addAttribute("errorMensaje", "No se encontró el check-in/out con ID " + id);
        }
        return "redirect:/check/listado";
    }
    
    @GetMapping("/buscar")
    public String mostrarFormularioBusqueda() {
        return "check/buscar";
    }
    
    @PostMapping("/buscar")
    public String buscar(@RequestParam("idCheck") Long idCheck, Model model) {
        CheckInOut check = service.buscarPorId(idCheck);
        if (check == null) {
            model.addAttribute("encontrado", false);
            model.addAttribute("mensaje", "No se encontró el check-in/out con ID " + idCheck);
        } else {
            model.addAttribute("encontrado", true);
            model.addAttribute("check", check);
        }
        return "check/buscar";
    }
}