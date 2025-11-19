package proyecto.com.model;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CheckInOut implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long idCheck;
    private Long idReserva;
    private LocalDate fechaEntrada;
    private LocalTime horaEntrada;
    private LocalDate fechaSalida;
    private LocalTime horaSalida;
    private String devolucion;
    
    // Constructor vacío
    public CheckInOut() {
    }
    
    // Constructor con todos los campos
    public CheckInOut(Long idCheck, Long idReserva, LocalDate fechaEntrada, 
                     LocalTime horaEntrada, LocalDate fechaSalida, 
                     LocalTime horaSalida, String devolucion) {
        this.idCheck = idCheck;
        this.idReserva = idReserva;
        this.fechaEntrada = fechaEntrada;
        this.horaEntrada = horaEntrada;
        this.fechaSalida = fechaSalida;
        this.horaSalida = horaSalida;
        this.devolucion = devolucion;
    }
}