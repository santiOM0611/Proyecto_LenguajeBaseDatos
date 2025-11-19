package proyecto.com.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TIPOS_HABITACIONES")
public class TipoHabitacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_hab_seq")
    @SequenceGenerator(name = "tipo_hab_seq", sequenceName = "SEQ_TIPOS_HABITACIONES", allocationSize = 1)
    @Column(name = "ID_TIPO_HABITACION")
    private Long idTipoHabitacion;
    
    @Column(name = "NOMBRE_TIPO_HABITACION", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "DESCRIPCION_TIPO_HABITACION", length = 500)
    private String descripcion;
    
    @Column(name = "ESTADO", length = 1)
    private String estado;
    
    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "FECHA_MODIFICACION")
    private LocalDateTime fechaModificacion;
    
    // Relación OneToMany con ServicioHabitacion
    @OneToMany(mappedBy = "tipoHabitacion", fetch = FetchType.LAZY)
    private List<ServicioHabitacion> servicios;
    
    // Constructor vacío
    public TipoHabitacion() {
        this.estado = "A";
    }
    
    // Constructor con parámetros
    public TipoHabitacion(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = "A";
    }
    
    // Getters y Setters
    public Long getIdTipoHabitacion() {
        return idTipoHabitacion;
    }
    
    public void setIdTipoHabitacion(Long idTipoHabitacion) {
        this.idTipoHabitacion = idTipoHabitacion;
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
    
    public List<ServicioHabitacion> getServicios() {
        return servicios;
    }
    
    public void setServicios(List<ServicioHabitacion> servicios) {
        this.servicios = servicios;
    }
    
    @Override
    public String toString() {
        return "TipoHabitacion{" +
                "idTipoHabitacion=" + idTipoHabitacion +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaModificacion=" + fechaModificacion +
                '}';
    }
}