package proyecto.com.controller;

import proyecto.com.model.Habitacion;
import proyecto.com.service.HabitacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/habitaciones")
public class HabitacionController {

    @Autowired
    private HabitacionService service;

    // Listar todas las habitaciones
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("habitaciones", service.listar());
        return "habitaciones/listar";
    }

    // Formulario para agregar
    @GetMapping("/agregar")
    public String agregar(Model model) {
        model.addAttribute("habitacion", new Habitacion());
        return "habitaciones/agregar";
    }

    // Guardar nueva habitación
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Habitacion habitacion, RedirectAttributes ra) {
        String msg = service.insertar(habitacion);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/habitaciones";
    }

    // Formulario para editar
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes ra) {
        Habitacion habitacion = service.obtenerPorId(id);
        if (habitacion == null) {
            ra.addFlashAttribute("error", "Habitación no encontrada");
            return "redirect:/habitaciones";
        }
        model.addAttribute("habitacion", habitacion);
        return "habitaciones/editar";
    }

    // Actualizar habitación
    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Habitacion habitacion, RedirectAttributes ra) {
        String msg = service.actualizar(habitacion);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/habitaciones";
    }

    // Eliminar habitación
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes ra) {
        String msg = service.eliminar(id);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/habitaciones";
    }

    // Ver detalles de una habitación
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable int id, Model model, RedirectAttributes ra) {
        Habitacion habitacion = service.obtenerPorId(id);
        if (habitacion == null) {
            ra.addFlashAttribute("error", "Habitación no encontrada");
            return "redirect:/habitaciones";
        }
        model.addAttribute("habitacion", habitacion);
        return "habitaciones/ver"; // HTML que incluye el fragmento verHabitacion
    }

    // Formulario de búsqueda por ID
    @GetMapping("/buscarID")
    public String buscarForm() {
        return "habitaciones/buscarID"; // HTML que incluye el fragmento buscarHabitacionID
    }

    // Procesar búsqueda por ID
    @PostMapping("/buscarID")
    public String buscarPorId(@RequestParam int idHabitacion, Model model) {
        Habitacion habitacion = service.obtenerPorId(idHabitacion);
        if (habitacion == null) {
            model.addAttribute("error", "No se encontró la habitación con ID " + idHabitacion);
            return "habitaciones/buscarID";
        }
        model.addAttribute("habitacion", habitacion);
        model.addAttribute("encontrado", true);
        return "habitaciones/buscarID";
    }
}
