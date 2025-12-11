package proyecto.com.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Detalles_Factura")
public class DetalleFactura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DetalleFactura")
    private Long id;
    
    @Column(name = "ID_Factura", nullable = false)
    private Long idFactura;
    
    @Column(name = "ID_Tipo_Habitacion")
    private Long idTipoHabitacion;
    
    @Column(name = "ID_Servicios")
    private Long idServicios;
    
    @Column(name = "Monto_Total", precision = 10, scale = 2)
    private BigDecimal montoTotal;
    
    public DetalleFactura() {}
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getIdFactura() {
        return idFactura;
    }
    
    public void setIdFactura(Long idFactura) {
        this.idFactura = idFactura;
    }
    
    public Long getIdTipoHabitacion() {
        return idTipoHabitacion;
    }
    
    public void setIdTipoHabitacion(Long idTipoHabitacion) {
        this.idTipoHabitacion = idTipoHabitacion;
    }
    
    public Long getIdServicios() {
        return idServicios;
    }
    
    public void setIdServicios(Long idServicios) {
        this.idServicios = idServicios;
    }
    
    public BigDecimal getMontoTotal() {
        return montoTotal;
    }
    
    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }
}