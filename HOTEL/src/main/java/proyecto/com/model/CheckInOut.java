/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.com.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Check_in_out")
public class CheckInOut {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Check")
    private Long idCheck;
    
    @Column(name = "ID_Reserva", nullable = false)
    private Long idReserva;
    
    @Column(name = "Fecha_Entrada")
    private LocalDate fechaEntrada;
    
    @Column(name = "Hora_Entrada")
    private LocalTime horaEntrada;
    
    @Column(name = "Fecha_Salida")
    private LocalDate fechaSalida;
    
    @Column(name = "Hora_Salida")
    private LocalTime horaSalida;
    
    @Transient
    private BigDecimal devolucion; // Este campo viene de la tabla relacionada
    
    // Getters y Setters
    public Long getIdCheck() { 
        return idCheck; 
    }
    
    public void setIdCheck(Long idCheck) { 
        this.idCheck = idCheck; 
    }
    
    public Long getIdReserva() { 
        return idReserva; 
    }
    
    public void setIdReserva(Long idReserva) { 
        this.idReserva = idReserva; 
    }
    
    public LocalDate getFechaEntrada() { 
        return fechaEntrada; 
    }
    
    public void setFechaEntrada(LocalDate fechaEntrada) { 
        this.fechaEntrada = fechaEntrada; 
    }
    
    public LocalTime getHoraEntrada() { 
        return horaEntrada; 
    }
    
    public void setHoraEntrada(LocalTime horaEntrada) { 
        this.horaEntrada = horaEntrada; 
    }
    
    public LocalDate getFechaSalida() { 
        return fechaSalida; 
    }
    
    public void setFechaSalida(LocalDate fechaSalida) { 
        this.fechaSalida = fechaSalida; 
    }
    
    public LocalTime getHoraSalida() { 
        return horaSalida; 
    }
    
    public void setHoraSalida(LocalTime horaSalida) { 
        this.horaSalida = horaSalida; 
    }
    
    public BigDecimal getDevolucion() { 
        return devolucion; 
    }
    
    public void setDevolucion(BigDecimal devolucion) { 
        this.devolucion = devolucion; 
    }
}