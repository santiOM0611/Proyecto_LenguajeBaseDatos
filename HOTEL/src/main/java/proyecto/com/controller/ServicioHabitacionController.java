package proyecto.com.controller;

import proyecto.com.model.ServicioHabitacion;
import proyecto.com.service.ServicioHabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;

@Controller
@RequestMapping("/ServiciosHabitacion")
public class ServicioHabitacionController {

    @Autowired
    private ServicioHabitacionService service;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("servicios", service.listarTodos());
        return "ServiciosHabitacion";
    }

    @GetMapping("/agregar")
    public String agregar(Model model) {
        model.addAttribute("servicio", new ServicioHabitacion());
        model.addAttribute("tiposHabitacion", service.listarTiposHabitacion());
        return "ServiciosHabitacion/agregar";
    }

    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute ServicioHabitacion servicio,
            @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {

        try {
            String mensaje = service.crear(servicio, imagenFile);
            redirectAttributes.addFlashAttribute("mensaje", mensaje);
            redirectAttributes.addFlashAttribute("tipo", "success");

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al guardar la imagen: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al guardar el servicio: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }

        return "redirect:/ServiciosHabitacion";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        ServicioHabitacion servicio = service.buscarPorId(id);

        if (servicio == null) {
            return "redirect:/ServiciosHabitacion";
        }

        model.addAttribute("servicio", servicio);
        model.addAttribute("tiposHabitacion", service.listarTiposHabitacion());

        return "ServiciosHabitacion/modifica";
    }

    @PostMapping("/actualizar")
    public String actualizar(
            @ModelAttribute ServicioHabitacion servicio,
            @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {

        try {
            String mensaje = service.actualizar(servicio, imagenFile);
            redirectAttributes.addFlashAttribute("mensaje", mensaje);
            redirectAttributes.addFlashAttribute("tipo", "success");

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al actualizar la imagen: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al actualizar el servicio: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }

        return "redirect:/ServiciosHabitacion";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model) {

        ServicioHabitacion servicio = service.buscarPorId(id);
        if (servicio == null) {
            return "redirect:/ServiciosHabitacion";
        }

        model.addAttribute("servicio", servicio);
        return "ServiciosHabitacion/ver";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        try {
            String mensaje = service.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", mensaje);
            redirectAttributes.addFlashAttribute("tipo", "success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al eliminar el servicio: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }

        return "redirect:/ServiciosHabitacion";
    }

    @GetMapping("/buscarID")
    public String buscarIDForm() {
        return "ServiciosHabitacion/buscarID";
    }

    @PostMapping("/buscarID")
    public String buscarID(@RequestParam("idServicio") Long idServicio, Model model) {

        try {
            ServicioHabitacion servicio = service.buscarPorId(idServicio);

            if (servicio != null) {
                model.addAttribute("servicio", servicio);
                model.addAttribute("encontrado", true);

            } else {
                model.addAttribute("error", "No se encontró el servicio con ID: " + idServicio);
            }

        } catch (Exception e) {
            model.addAttribute("error", "Error al buscar el servicio: " + e.getMessage());
        }

        return "ServiciosHabitacion/buscarID";
    }

    @GetMapping("/tipo/{idTipo}")
    public String listarPorTipo(@PathVariable Long idTipo, Model model) {

        model.addAttribute("servicios", service.listarPorTipo(idTipo));
        model.addAttribute("tiposHabitacion", service.listarTiposHabitacion());

        return "ServiciosHabitacion/ServiciosHabitacion";
    }
}
