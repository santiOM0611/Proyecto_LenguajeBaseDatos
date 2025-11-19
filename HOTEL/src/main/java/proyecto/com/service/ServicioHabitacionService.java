package proyecto.com.service;

import proyecto.com.model.ServicioHabitacion;
import proyecto.com.model.TipoHabitacion;
import proyecto.com.repository.ServicioHabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ServicioHabitacionService {
    
    @Autowired
    private ServicioHabitacionRepository repository;
    
    @Autowired
    private TipoHabitacionRepository tipoHabitacionRepository;
    
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/servicios/";
    
    public List<ServicioHabitacion> listarTodos() {
        return repository.findAllActivos();
    }
    
    public List<ServicioHabitacion> listarPorTipo(Long idTipo) {
        return repository.findByIdTipo(idTipo);
    }
    
    public ServicioHabitacion buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
    
    // Método para obtener todos los tipos de habitación activos
    public List<TipoHabitacion> listarTiposHabitacion() {
        return tipoHabitacionRepository.findAllActivos();
    }
    
    // Método para obtener el próximo ID disponible
    public Long obtenerProximoId() {
        return repository.obtenerProximoId();
    }
    
    @Transactional
    public String crear(ServicioHabitacion servicio, MultipartFile imagen) throws IOException {
        String rutaImagen = null;
        
        if (imagen != null && !imagen.isEmpty()) {
            rutaImagen = guardarImagen(imagen);
        }
        
        // Obtener el próximo ID de la secuencia
        Long nuevoId = obtenerProximoId();
        
        // Llamada al procedimiento AGREGAR con el ID generado
        String mensaje = repository.agregarServicioHabitacion(
            nuevoId,
            servicio.getIdTipo(),
            servicio.getNombre(),
            servicio.getDescripcion(),
            rutaImagen
        );
        
        return mensaje;
    }
    
    @Transactional
    public String actualizar(ServicioHabitacion servicio, MultipartFile imagen) throws IOException {
        String rutaImagen = servicio.getRutaImagen();
        
        if (imagen != null && !imagen.isEmpty()) {
            // Eliminar imagen anterior si existe
            if (rutaImagen != null && !rutaImagen.isEmpty()) {
                eliminarImagen(rutaImagen);
            }
            rutaImagen = guardarImagen(imagen);
        }
        
        // Llamada al procedimiento EDITAR
        String mensaje = repository.editarServicioHabitacion(
            servicio.getIdServicioHab(),
            servicio.getIdTipo(),
            servicio.getNombre(),
            servicio.getDescripcion(),
            rutaImagen
        );
        
        return mensaje;
    }
    
    @Transactional
    public String eliminar(Long id) {
        // Obtener servicio para eliminar imagen
        ServicioHabitacion servicio = buscarPorId(id);
        if (servicio != null && servicio.getRutaImagen() != null) {
            eliminarImagen(servicio.getRutaImagen());
        }
        
        // Llamada al procedimiento ELIMINAR
        String mensaje = repository.eliminarServicioHabitacion(id);
        return mensaje;
    }
    
    private String guardarImagen(MultipartFile imagen) throws IOException {
        // Crear directorio si no existe
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        // Generar nombre único para la imagen
        String nombreOriginal = imagen.getOriginalFilename();
        String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
        String nombreUnico = UUID.randomUUID().toString() + extension;
        
        // Guardar archivo
        Path rutaArchivo = uploadPath.resolve(nombreUnico);
        Files.copy(imagen.getInputStream(), rutaArchivo);
        
        return "/uploads/servicios/" + nombreUnico;
    }
    
    private void eliminarImagen(String rutaImagen) {
        try {
            Path path = Paths.get("src/main/resources/static" + rutaImagen);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}