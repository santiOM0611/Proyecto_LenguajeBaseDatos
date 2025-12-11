package proyecto.com.controller;

import proyecto.com.model.Empleado;
import proyecto.com.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService service;

    // ---------------- LISTAR ----------------
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("empleados", service.listar());
        return "empleados/empleados"; // plantilla con fragmento listadoEmpleados
    }

    // ---------------- AGREGAR ----------------
    @GetMapping("/agregar")
    public String agregarForm(Model model) {
        model.addAttribute("empleado", new Empleado());
        return "empleados/agregar"; // plantilla con fragmento agregarEmpleado
    }

    @PostMapping("/guardar")
    public String guardarEmpleado(@ModelAttribute Empleado empleado, RedirectAttributes ra) {
        String msg = service.guardarEmpleado(empleado);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/empleados";
    }

    // ---------------- EDITAR ----------------
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable int id, Model model) {
        model.addAttribute("empleado", service.obtenerPorId(id));
        return "empleados/editar"; // plantilla con fragmento editarEmpleado
    }

    @PostMapping("/actualizar")
    public String actualizarEmpleado(@ModelAttribute Empleado empleado, RedirectAttributes ra) {
        String msg = service.actualizarEmpleado(empleado);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/empleados";
    }

    // ---------------- ELIMINAR ----------------
    @GetMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable int id, RedirectAttributes ra) {
        String msg = service.eliminarEmpleado(id);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/empleados";
    }

    // ---------------- BUSCAR POR ID - FORMULARIO ----------------
    @GetMapping("/buscarID")
    public String buscarIDForm(Model model) {
        model.addAttribute("empleado", new Empleado());
        return "empleados/buscarID"; // plantilla con fragmento buscarEmpleadoID
    }

    // ---------------- BUSCAR POR ID - PROCESAR FORM ----------------
    @PostMapping("/buscarID")
    public String buscarIDSubmit(@RequestParam("idEmpleado") int idEmpleado, Model model) {
        Empleado empleado = service.obtenerPorId(idEmpleado);
        if (empleado != null) {
            model.addAttribute("empleado", empleado);
            model.addAttribute("encontrado", true);
        } else {
            model.addAttribute("error", "Empleado no encontrado");
        }
        return "empleados/buscarID";
    }

   
}
