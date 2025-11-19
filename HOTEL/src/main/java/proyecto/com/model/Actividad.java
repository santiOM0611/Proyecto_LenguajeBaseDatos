/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Actividades")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Actividad")
    private Long id;

    @Column(name = "ID_Hotel", nullable = false)
    private Long idHotel;

    @Column(name = "Nombre_Actividad", length = 100, nullable = false)
    private String nombre;

    @Column(name = "Descripcion_Actividad", length = 200)
    private String descripcion;

    @Column(name = "Capacidad_Actividad")
    private Integer capacidad;

    @Column(name = "ruta_imagen", length = 300)
    private String rutaImagen;  

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdHotel() { return idHotel; }
    public void setIdHotel(Long idHotel) { this.idHotel = idHotel; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }

    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
}
