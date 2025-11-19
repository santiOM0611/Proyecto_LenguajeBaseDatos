package proyecto.com.controller;

import proyecto.com.model.Hotel;
import proyecto.com.service.HotelService;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("hoteles", service.listar());
        return "hoteles/hoteles";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable int id, Model model) {
        model.addAttribute("hotel", service.obtenerPorId(id));
        return "hoteles/ver";
    }

    @GetMapping("/agregar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("hotel", new Hotel());
        return "hoteles/agregar";
    }

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
        String msg = service.actualizarHotel(hotel);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/hoteles";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes ra) {
        String msg = service.eliminar(id);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/hoteles";
    }

    @GetMapping("/buscarID")
    public String mostrarFormularioBusquedaID(Model model) {
        model.addAttribute("hotel", new Hotel());
        return "hoteles/buscarID";
    }

    @PostMapping("/buscarID")
    public String buscarPorId(@RequestParam("idHotel") int idHotel, Model model, RedirectAttributes ra) {
        Hotel hotel = service.buscarPorId(idHotel);

        if (hotel != null) {
            model.addAttribute("hotel", hotel);
            model.addAttribute("encontrado", true);
            return "hoteles/buscarID";
        } else {
            ra.addFlashAttribute("error", "No se encontró ningún hotel con el ID: " + idHotel);
            return "redirect:/hoteles/buscarID";
        }
    }
}
