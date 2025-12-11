package proyecto.com.controller;

import proyecto.com.model.Empleado;
import proyecto.com.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("empleados", service.listar());
        return "empleados/empleados";
    }

    @GetMapping("/ver/{cedula}")
    public String ver(@PathVariable Long cedula, Model model) {
        model.addAttribute("empleado", service.obtenerPorId(cedula));
        return "empleados/ver";
    }

    @GetMapping("/agregar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("empleado", new Empleado());
        return "empleados/agregar";
    }

    @PostMapping("/guardar")
    public String guardarEmpleado(@ModelAttribute Empleado empleado, RedirectAttributes ra) {
        String msg = service.guardarEmpleado(empleado);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/empleados";
    }

    @GetMapping("/editar/{cedula}")
    public String editarEmpleado(@PathVariable Long cedula, Model model) {
        model.addAttribute("empleado", service.obtenerPorId(cedula));
        return "empleados/modifica";
    }

    @PostMapping("/actualizar")
    public String actualizarEmpleado(@ModelAttribute Empleado empleado, RedirectAttributes ra) {
        String msg = service.actualizarEmpleado(empleado);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/empleados";
    }

    @GetMapping("/eliminar/{cedula}")
    public String eliminar(@PathVariable Long cedula, RedirectAttributes ra) {
        String msg = service.eliminar(cedula);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/empleados";
    }

    @GetMapping("/buscarID")
    public String mostrarFormularioBusquedaID(Model model) {
        model.addAttribute("empleado", new Empleado());
        return "empleados/buscarID";
    }

    @PostMapping("/buscarID")
    public String buscarPorId(@RequestParam("cedula") Long cedula, Model model, RedirectAttributes ra) {
        Empleado empleado = service.buscarPorId(cedula);

        if (empleado != null) {
            model.addAttribute("empleado", empleado);
            model.addAttribute("encontrado", true);
            return "empleados/buscarID";
        } else {
            ra.addFlashAttribute("error", "No se encontró ningún empleado con la cédula: " + cedula);
            return "redirect:/empleados/buscarID";
        }
    }
}
