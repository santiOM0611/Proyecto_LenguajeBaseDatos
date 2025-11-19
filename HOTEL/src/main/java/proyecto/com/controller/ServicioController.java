/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.controller;

import proyecto.com.model.Servicio;
import proyecto.com.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/servicio")
public class ServicioController {
    
    @Autowired
    private ServicioService service;
    
    @GetMapping("/listado")
    public String listadoServicios(Model model) {
        model.addAttribute("servicios", service.listarTodos());
        return "servicio/listado";
    }
    
    @GetMapping("/agregar")
    public String nuevoServicio(Model model) {
        model.addAttribute("servicio", new Servicio());
        return "servicio/agregar";
    }
    
    @PostMapping("/guardar")
    public String guardarServicio(@ModelAttribute Servicio servicio, Model model) {


        if (servicio.getRutaImagen() == null || servicio.getRutaImagen().isEmpty()) {
            servicio.setRutaImagen("default.jpg");
        }
        
        try {
            service.guardar(servicio);
            return "redirect:/servicio/listado";
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            model.addAttribute("errorMensaje", "El hotel con ID " + servicio.getIdHotel() + " no existe en el sistema");
            model.addAttribute("servicio", servicio);
            return "servicio/agregar";
        }
    }
    
    @GetMapping("/editar/{id}")
    public String editarServicio(@PathVariable Long id, Model model) {
        Optional<Servicio> servicio = service.obtenerPorId(id);
        if (servicio.isPresent()) {
            model.addAttribute("servicio", servicio.get());
            return "servicio/modifica";
        }
        return "redirect:/servicio/listado";
    }
    
    @GetMapping("/ver/{id}")
    public String verServicio(@PathVariable Long id, Model model) {
        Optional<Servicio> servicio = service.obtenerPorId(id);
        if (servicio.isPresent()) {
            model.addAttribute("servicio", servicio.get());
            return "servicio/ver";
        }
        return "redirect:/servicio/listado";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarServicio(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/servicio/listado";
    }
    

    
    @GetMapping("/buscar")
    public String mostrarFormularioBusqueda(Model model) {
        model.addAttribute("encontrado", null);
        return "servicio/buscar";  
    }
    

    
    @PostMapping("/buscar")
    public String buscar(@RequestParam("idServicio") Long idServicio, Model model) {
        

        Servicio servicio = service.buscarPorId(idServicio); 


        if (servicio == null) {
            model.addAttribute("encontrado", false);
            model.addAttribute("mensaje", "No se encontró el servicio con ID " + idServicio);
        } else {
            model.addAttribute("encontrado", true);
            model.addAttribute("servicio", servicio);
        }

        return "servicio/buscar";
    }
}
