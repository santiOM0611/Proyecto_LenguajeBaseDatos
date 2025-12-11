package proyecto.com.controller;

import proyecto.com.model.Habitaciones;
import proyecto.com.service.HabitacionesService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/habitaciones")
public class HabitacionesController {

    @Autowired
    private HabitacionesService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("habitaciones", service.listar());
        return "habitaciones/habitaciones";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable int id, Model model) {
        model.addAttribute("habitacion", service.obtenerPorId(id));
        return "habitaciones/ver";
    }

    @GetMapping("/agregar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("habitacion", new Habitaciones());
        return "habitaciones/agregar";
    }

    @PostMapping("/guardar")
    public String guardarHabitacion(@ModelAttribute Habitaciones habitacion, RedirectAttributes ra) {
        String msg = service.guardarHabitacion(habitacion);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/habitaciones";
    }

    @GetMapping("/editar/{id}")
    public String editarHabitacion(@PathVariable int id, Model model) {
        model.addAttribute("habitacion", service.obtenerPorId(id));
        return "habitaciones/modifica";
    }

    @PostMapping("/actualizar")
    public String actualizarHabitacion(@ModelAttribute Habitaciones habitacion, RedirectAttributes ra) {
        String msg = service.actualizarHabitacion(habitacion);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/habitaciones";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes ra) {
        String msg = service.eliminar(id);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/habitaciones";
    }

    @GetMapping("/buscarID")
    public String mostrarFormularioBusquedaID(Model model) {
        model.addAttribute("habitacion", new Habitaciones());
        return "habitaciones/buscarID";
    }

    @PostMapping("/buscarID")
    public String buscarPorId(@RequestParam("idHabitacion") int idHabitacion, Model model, RedirectAttributes ra) {
        Habitaciones habitacion = service.buscarPorId(idHabitacion);

        if (habitacion != null) {
            model.addAttribute("habitacion", habitacion);
            model.addAttribute("encontrado", true);
            return "habitaciones/buscarID";
        } else {
            ra.addFlashAttribute("error", "No se encontró ninguna habitación con el ID: " + idHabitacion);
            return "redirect:/habitaciones/buscarID";
        }
    }
}
