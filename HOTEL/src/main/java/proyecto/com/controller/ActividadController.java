/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.controller;

import proyecto.com.model.Actividad;
import proyecto.com.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/actividad")
public class ActividadController {
    
    @Autowired
    private ActividadService service;
    

    @GetMapping("/listado")
    public String listadoActividades(Model model) {
        model.addAttribute("actividades", service.listarTodas());
        return "actividad/listado";
    }
    

    @GetMapping("/agregar")
    public String nuevaActividad(Model model) {
        model.addAttribute("actividad", new Actividad());
        return "actividad/agregar";
    }
    

    @PostMapping("/guardar")
    public String guardarActividad(@ModelAttribute Actividad actividad, Model model) {

        if (actividad.getRutaImagen() == null || actividad.getRutaImagen().isEmpty()) {
            actividad.setRutaImagen("default.jpg");
        }
        
        try {
            service.guardar(actividad);
            return "redirect:/actividad/listado";
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            model.addAttribute("errorMensaje", "El hotel con ID " + actividad.getIdHotel() + " no existe en el sistema");
            model.addAttribute("actividad", actividad);
            return "actividad/agregar";
        }
    }
    

    @GetMapping("/editar/{id}")
    public String editarActividad(@PathVariable Long id, Model model) {
        Optional<Actividad> actividad = service.obtenerPorId(id);
        if (actividad.isPresent()) {
            model.addAttribute("actividad", actividad.get());
            return "actividad/modifica";
        }
        return "redirect:/actividad/listado";
    }
    

    @GetMapping("/ver/{id}")
    public String verActividad(@PathVariable Long id, Model model) {
        Optional<Actividad> actividad = service.obtenerPorId(id);
        if (actividad.isPresent()) {
            model.addAttribute("actividad", actividad.get());
            return "actividad/ver";
        }
        return "redirect:/actividad/listado";
    }
    

    @GetMapping("/eliminar/{id}")
    public String eliminarActividad(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/actividad/listado";
    }
    
    @GetMapping("/buscar")
    public String mostrarFormularioBusqueda() {
        return "actividad/buscar";
    }
    
    @PostMapping("/buscar")
    public String buscar(@RequestParam("idActividad") Long idActividad, Model model) {
        Actividad actividad = service.buscarPorId(idActividad);
        if (actividad == null) {
            model.addAttribute("encontrado", false);
            model.addAttribute("mensaje", "No se encontró la actividad con ID " + idActividad);
        } else {
            model.addAttribute("encontrado", true);
            model.addAttribute("actividad", actividad);
        }
        return "actividad/buscar";
    }
}