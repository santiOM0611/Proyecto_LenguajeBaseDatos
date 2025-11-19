package proyecto.com.controller;

import proyecto.com.model.Hotel;
import proyecto.com.service.HotelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/hoteles")
public class HotelController {

    @Autowired
    private HotelService service;

// Listar todos los hoteles
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("hoteles", service.listar());
        return "hoteles/hoteles";
    }

// Ver detalles de un hotel
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable int id, Model model) {
        model.addAttribute("hotel", service.obtenerPorId(id));
        return "hoteles/ver";
    }

// Mostrar formulario para agregar hotel
    @GetMapping("/agregar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("hotel", new Hotel());
        return "hoteles/agregar"; // ya no enviamos provincias, cantones ni distritos
    }

// Guardar hotel usando nombres (todo via paquete)
    @PostMapping("/guardar")
    public String guardarHotel(@ModelAttribute Hotel hotel, RedirectAttributes ra) {
        String msg = service.guardarHotel(hotel);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/hoteles";
    }

    @GetMapping("/editar/{id}")
    public String editarHotel(@PathVariable int id, Model model) {
        model.addAttribute("hotel", service.obtenerPorId(id));
        return "hoteles/modifica";
    }

    @PostMapping("/actualizar")
    public String actualizarHotel(@ModelAttribute Hotel hotel, RedirectAttributes ra) {
        String msg = service.actualizarHotel(hotel); // ✔ ahora sí actualiza
        ra.addFlashAttribute("msg", msg);
        return "redirect:/hoteles";
    }

// Eliminar hotel
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes ra) {
        String msg = service.eliminar(id);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/hoteles";
    }

}
