package proyecto.com.controller;

import java.time.LocalDate;
import proyecto.com.model.Reservacion;
import proyecto.com.service.ReservacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import proyecto.com.model.Factura;

@Controller
@RequestMapping("/reservas")
public class ReservacionController {

    @Autowired
    private ReservacionService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("reservas", service.listar());
        return "reservas/reservas";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model) {
        model.addAttribute("reserva", service.buscarPorIdEnVista(id));
        return "reservas/ver";
    }

    @GetMapping("/agregar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("reserva", new Reservacion());
        return "reservas/agregar";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Reservacion reserva, RedirectAttributes ra) {
        String msg = service.guardarReservacion(reserva);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/reservas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Reservacion r = service.obtenerPorId(id);
        model.addAttribute("reserva", r);
        return "reservas/modifica";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Reservacion reserva, RedirectAttributes ra) {
        String msg = service.actualizarReservacion(reserva);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/reservas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        String msg = service.eliminarReservacion(id);
        ra.addFlashAttribute("msg", msg);
        return "redirect:/reservas";
    }

    @GetMapping("/buscarID")
    public String mostrarBuscarID(Model model) {
        model.addAttribute("reserva", new Reservacion());
        return "reservas/buscarID";
    }

    @PostMapping("/buscarID")
    public String buscarPorId(@RequestParam("idReserva") Long idReserva, Model model, RedirectAttributes ra) {
        Reservacion r = service.buscarPorIdEnVista(idReserva);
        if (r != null) {
            model.addAttribute("reserva", r);
            model.addAttribute("encontrado", true);
            return "reservas/buscarID";
        } else {
            ra.addFlashAttribute("error", "No se encontró la reservación con ID: " + idReserva);
            return "redirect:/reservas/buscarID";
        }
    }

    @GetMapping("/agregar/{idReserva}")
    public String agregarDesdeReserva(@PathVariable Integer idReserva, Model model) {
        Factura f = new Factura();
        f.setIdReserva(idReserva);
        f.setFecha(LocalDate.now());
        model.addAttribute("factura", f);
        return "facturas/agregar";
    }

}
