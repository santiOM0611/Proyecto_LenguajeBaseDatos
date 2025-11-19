package proyecto.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proyecto.com.model.Cliente;
import proyecto.com.service.ClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", service.listar());
        return "cliente/clientes";  // ← Cambio aquí
    }

    @GetMapping("/agregar")
    public String agregar(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente/agregarCliente";  // ← Cambio aquí
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente) {
        service.guardar(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/buscar/{cedula}")
    public String buscar(@PathVariable String cedula, Model model) {
        model.addAttribute("cliente", service.buscar(cedula));
        return "cliente/resultadoBusqueda";  // ← Cambio aquí
    }

    @GetMapping("/editar/{cedula}")
    public String editar(@PathVariable String cedula, Model model) {
        model.addAttribute("cliente", service.buscar(cedula));
        return "cliente/editarCliente";  // ← Cambio aquí
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Cliente cliente) {
        service.actualizar(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/eliminar/{cedula}")
    public String eliminar(@PathVariable String cedula) {
        service.eliminar(cedula);
        return "redirect:/clientes";
    }
}
