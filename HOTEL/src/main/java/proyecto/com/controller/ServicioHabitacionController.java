/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.controller;

import proyecto.com.model.ServicioHabitacion;
import proyecto.com.service.ServicioHabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/servicioHabitacion")
public class ServicioHabitacionController {
    
    @Autowired
    private ServicioHabitacionService service;
    
    @GetMapping("/listado")
    public String listadoServiciosHabitacion(Model model) {
        model.addAttribute("serviciosHabitacion", service.listarTodos());
        return "servicioHabitacion/listado";
    }
    
    @GetMapping("/agregar")
    public String nuevoServicioHabitacion(Model model) {
        model.addAttribute("servicioHabitacion", new ServicioHabitacion());
        return "servicioHabitacion/agregar";
    }
    
    @PostMapping("/guardar")
    public String guardarServicioHabitacion(@ModelAttribute ServicioHabitacion servicioHabitacion, Model model) {
        if (servicioHabitacion.getRutaImagen() == null || servicioHabitacion.getRutaImagen().isEmpty()) {
            servicioHabitacion.setRutaImagen("default.jpg");
        }
        
        try {
            service.guardar(servicioHabitacion);
            return "redirect:/servicioHabitacion/listado";
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            model.addAttribute("errorMensaje", "El tipo de habitación con ID " + servicioHabitacion.getIdTipoHabitacion() + " no existe en el sistema");
            model.addAttribute("servicioHabitacion", servicioHabitacion);
            return "servicioHabitacion/agregar";
        }
    }
    
    @GetMapping("/editar/{id}")
    public String editarServicioHabitacion(@PathVariable Long id, Model model) {
        Optional<ServicioHabitacion> servicioHabitacion = service.obtenerPorId(id);
        if (servicioHabitacion.isPresent()) {
            model.addAttribute("servicioHabitacion", servicioHabitacion.get());
            return "servicioHabitacion/modifica";
        } else {
            model.addAttribute("errorMensaje", "No se encontró el servicio de habitación con ID " + id);
            return "redirect:/servicioHabitacion/listado";
        }
    }
    
    @GetMapping("/ver/{id}")
    public String verServicioHabitacion(@PathVariable Long id, Model model) {
        Optional<ServicioHabitacion> servicioHabitacion = service.obtenerPorId(id);
        if (servicioHabitacion.isPresent()) {
            model.addAttribute("servicioHabitacion", servicioHabitacion.get());
            return "servicioHabitacion/ver";
        } else {
            model.addAttribute("errorMensaje", "No se encontró el servicio de habitación con ID " + id);
            return "redirect:/servicioHabitacion/listado";
        }
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarServicioHabitacion(@PathVariable Long id, Model model) {
        Optional<ServicioHabitacion> servicioHabitacion = service.obtenerPorId(id);
        if (servicioHabitacion.isPresent()) {
            service.eliminar(id);
        } else {
            model.addAttribute("errorMensaje", "No se encontró el servicio de habitación con ID " + id);
        }
        return "redirect:/servicioHabitacion/listado";
    }
    
    @GetMapping("/buscar")
    public String mostrarFormularioBusqueda() {
        return "servicioHabitacion/buscar";
    }
    
    @PostMapping("/buscar")
    public String buscar(@RequestParam("idServicioHabitacion") Long idServicioHabitacion, Model model) {
        ServicioHabitacion servicioHabitacion = service.buscarPorId(idServicioHabitacion);
        if (servicioHabitacion == null) {
            model.addAttribute("encontrado", false);
            model.addAttribute("mensaje", "No se encontró el servicio de habitación con ID " + idServicioHabitacion);
        } else {
            model.addAttribute("encontrado", true);
            model.addAttribute("servicioHabitacion", servicioHabitacion);
        }
        return "servicioHabitacion/buscar";
    }
}