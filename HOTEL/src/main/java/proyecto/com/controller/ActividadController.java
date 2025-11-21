/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.controller;

import proyecto.com.model.Actividad;
import proyecto.com.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/actividad")
public class ActividadController {

    @Autowired
    private ActividadService service;


    @GetMapping("/listado")
    public String listadoActividades(Model model) {
        model.addAttribute("actividades", service.listarTodas());
        return "actividad/listado";
    }


    @GetMapping("/agregar")
    public String nuevaActividad(Model model) {
        model.addAttribute("actividad", new Actividad());
        return "actividad/agregar";
    }


    @PostMapping("/guardar")
    public String guardarActividad(@ModelAttribute Actividad actividad, Model model) {

        if (actividad.getRutaImagen() == null || actividad.getRutaImagen().isEmpty()) {
            actividad.setRutaImagen("default.jpg");
        }

        String mensaje = service.guardar(actividad);

        if (!mensaje.contains("correctamente")) {
            model.addAttribute("errorMensaje", mensaje);
            model.addAttribute("actividad", actividad);
            return "actividad/agregar";
        }

        return "redirect:/actividad/listado";
    }



    @GetMapping("/editar/{id}")
public String editarActividad(@PathVariable Long id, Model model) {

    Actividad actividad = service.listarTodas()
            .stream()
            .filter(a -> a.getId().equals(id))
            .findFirst()
            .orElse(null);

    if (actividad == null) {
        model.addAttribute("errorMensaje", "La actividad no existe");
        return "redirect:/actividad/listado";
    }

    model.addAttribute("actividad", actividad);
    return "actividad/modifica";
}




    @PostMapping("/actualizar")
    public String actualizarActividad(@ModelAttribute Actividad actividad, Model model) {

        if (actividad.getRutaImagen() == null || actividad.getRutaImagen().isEmpty()) {
            actividad.setRutaImagen("default.jpg");
        }

        String mensaje = service.actualizar(actividad);

        if (!mensaje.contains("correctamente")) {
            model.addAttribute("errorMensaje", mensaje);
            model.addAttribute("actividad", actividad);
            return "actividad/modifica";
        }

        return "redirect:/actividad/listado";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarActividad(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/actividad/listado";
    }
    @GetMapping("/ver/{id}")
public String verActividad(@PathVariable Long id, Model model) {

    Actividad actividad = service.listarTodas()
            .stream()
            .filter(a -> a.getId().equals(id))
            .findFirst()
            .orElse(null);

    if (actividad == null) {
        model.addAttribute("errorMensaje", "La actividad no existe");
        return "redirect:/actividad/listado";
    }

    model.addAttribute("actividad", actividad);
    return "actividad/ver"; 
}

}
