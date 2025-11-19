package proyecto.com.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "SERVICIOS_HABITACIONES")
public class ServicioHabitacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servicio_hab_seq")
    @SequenceGenerator(name = "servicio_hab_seq", sequenceName = "SEQ_SERVICIOS_HABITACIONES", allocationSize = 1)
    @Column(name = "ID_SERVICIO_HABITACION")
    private Long idServicioHab;
    
    @Column(name = "ID_TIPO_HABITACION", nullable = false)
    private Long idTipo;
    
    @Column(name = "NOMBRE_SERVICIO_HABITACION", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "DESCRIPCION_SERVICIO_HABITACION", length = 500)
    private String descripcion;
    
    @Column(name = "RUTA_IMAGEN", length = 255)
    private String rutaImagen;
    
    @Column(name = "ESTADO", length = 1)
    private String estado;
    
    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "FECHA_MODIFICACION")
    private LocalDateTime fechaModificacion;
    
    // Relación con TipoHabitacion (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_HABITACION", insertable = false, updatable = false)
    private TipoHabitacion tipoHabitacion;
    
    // Constructor vacío
    public ServicioHabitacion() {
        this.estado = "A";
    }
    
    // Constructor con parámetros principales
    public ServicioHabitacion(Long idTipo, String nombre, String descripcion) {
        this.idTipo = idTipo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = "A";
    }
    
    // Getters y Setters
    public Long getIdServicioHab() {
        return idServicioHab;
    }
    
    public void setIdServicioHab(Long idServicioHab) {
        this.idServicioHab = idServicioHab;
    }
    
    public Long getIdTipo() {
        return idTipo;
    }
    
    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getRutaImagen() {
        return rutaImagen;
    }
    
    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }
    
    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    public TipoHabitacion getTipoHabitacion() {
        return tipoHabitacion;
    }
    
    public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }
    
    // Método auxiliar para obtener el nombre del tipo de habitación
    public String getNombreTipoHabitacion() {
        return tipoHabitacion != null ? tipoHabitacion.getNombre() : "";
    }
    
    @Override
    public String toString() {
        return "ServicioHabitacion{" +
                "idServicioHab=" + idServicioHab +
                ", idTipo=" + idTipo +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", rutaImagen='" + rutaImagen + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaModificacion=" + fechaModificacion +
                '}';
    }
}