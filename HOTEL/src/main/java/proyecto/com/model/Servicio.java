/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package proyecto.com.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Servicios")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Servicio")
    private Long id;

    @Column(name = "ID_Hotel", nullable = false)
    private Long idHotel;

    @Column(name = "Nombre_Servicio", length = 100)
    private String nombre;

    @Column(name = "Descripcion_Servicio", length = 250)
    private String descripcion;

    @Column(name = "Costo_Servicio", precision = 10, scale = 2)
    private BigDecimal costo;

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

    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }

    public String getRutaImagen() { return rutaImagen; } 
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }  
}

