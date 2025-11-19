package proyecto.com.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Servicios_Habitaciones")
public class ServicioHabitacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Servicio_Habitacion")
    private Long id;
    
    @Column(name = "ID_Tipo_Habitacion", nullable = false, insertable = false, updatable = false)
    private Long idTipoHabitacion;
    
    @Column(name = "Nombre_Servicio_Habitacion", length = 100)
    private String nombre;
    
    @Column(name = "Descripcion_Servicio_Habitacion", length = 250)
    private String descripcion;
    
    @Column(name = "Ruta_Imagen", length = 300)
    private String rutaImagen;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Tipo_Habitacion", referencedColumnName = "ID_Tipo_Habitacion")
    private TipoHabitacion tipoHabitacion;
    
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
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
    
    public String getRutaImagen() { 
        return rutaImagen; 
    }
    
    public void setRutaImagen(String rutaImagen) { 
        this.rutaImagen = rutaImagen; 
    }
    
    // NUEVO GETTER Y SETTER
    public TipoHabitacion getTipoHabitacion() {
        return tipoHabitacion;
    }
    
    public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }
}