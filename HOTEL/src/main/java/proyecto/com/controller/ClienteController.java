package proyecto.com.controller;

import proyecto.com.model.Cliente;
import proyecto.com.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", service.listar());
        return "clientes/clientes";
    }

    @GetMapping("/ver/{cedula}")
    public String ver(@PathVariable Long cedula, Model model) {
        model.addAttribute("cliente", service.obtenerPorId(cedula));
        return "clientes/ver";
    }

    @GetMapping("/agregar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/agregar";
    }

    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente, RedirectAttributes ra) {
        String msg = service.guardarCliente(cliente);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{cedula}")
    public String editarCliente(@PathVariable Long cedula, Model model) {
        model.addAttribute("cliente", service.obtenerPorId(cedula));
        return "clientes/modificar";
    }

    @PostMapping("/actualizar")
    public String actualizarCliente(@ModelAttribute Cliente cliente, RedirectAttributes ra) {
        String msg = service.actualizarCliente(cliente);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/clientes";
    }

    @GetMapping("/eliminar/{cedula}")
    public String eliminar(@PathVariable Long cedula, RedirectAttributes ra) {
        String msg = service.eliminar(cedula);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/clientes";
    }

    @GetMapping("/buscarID")
    public String mostrarFormularioBusquedaID(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/buscarID";
    }

    @PostMapping("/buscarID")
    public String buscarPorId(@RequestParam("cedula") Long cedula, Model model, RedirectAttributes ra) {
        Cliente cliente = service.buscarPorId(cedula);

        if (cliente != null) {
            model.addAttribute("cliente", cliente);
            model.addAttribute("encontrado", true);
            return "clientes/buscarID";
        } else {
            ra.addFlashAttribute("error", "No se encontró ningún cliente con la cédula: " + cedula);
            return "redirect:/clientes/buscarID";
        }
    }
}