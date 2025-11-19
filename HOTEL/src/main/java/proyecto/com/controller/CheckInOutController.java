package proyecto.com.controller;

import proyecto.com.model.CheckInOut;
import proyecto.com.service.CheckInOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/check")
public class CheckInOutController {

    @Autowired
    private CheckInOutService service;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("checks", service.listarTodos());
        return "Check/listado";
    }

    @GetMapping("/agregar")
    public String agregar(Model model) {
        model.addAttribute("check", new CheckInOut());
        model.addAttribute("reservaciones", service.listarReservaciones());
        return "Check/agregar";
    }

    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute CheckInOut check,
            RedirectAttributes redirectAttributes) {
        try {
            String mensaje = service.crear(check);
            redirectAttributes.addFlashAttribute("mensaje", mensaje);
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al guardar el check: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }
        return "redirect:/check/listado";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        CheckInOut check = service.buscarPorId(id);
        if (check == null) {
            return "redirect:/check";
        }
        model.addAttribute("check", check);
        model.addAttribute("reservaciones", service.listarReservaciones());
        return "Check/modifica";
    }

    @PostMapping("/actualizar")
    public String actualizar(
            @ModelAttribute CheckInOut check,
            RedirectAttributes redirectAttributes) {
        try {
            String mensaje = service.actualizar(check);
            redirectAttributes.addFlashAttribute("mensaje", mensaje);
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al actualizar el check: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }
        return "redirect:/check/listado";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model) {
        CheckInOut check = service.buscarPorId(id);
        if (check == null) {
            return "redirect:/check/listado";
        }
        model.addAttribute("check", check);
        return "Check/ver";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            String mensaje = service.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", mensaje);
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al eliminar el check: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }
        return "redirect:/check";
    }

    @GetMapping("/buscarID")
    public String buscarIDForm() {
        return "Check/buscarID";
    }

    @PostMapping("/buscarID")
    public String buscarID(@RequestParam("idCheck") Long idCheck, Model model) {
        try {
            CheckInOut check = service.buscarPorId(idCheck);
            if (check != null) {
                model.addAttribute("check", check);
                model.addAttribute("encontrado", true);
            } else {
                model.addAttribute("error", "No se encontró el check con ID: " + idCheck);
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al buscar el check: " + e.getMessage());
        }
        return "Check/buscarID";
    }
}