package proyecto.com.model;

import java.time.LocalDate;

public class Reservacion {
    private Long idReserva;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private Long cedula;
    private Integer idTipoHabitacion; 

    public Long getIdReserva() { return idReserva; }
    public void setIdReserva(Long idReserva) { this.idReserva = idReserva; }

    public LocalDate getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDate fechaEntrada) { this.fechaEntrada = fechaEntrada; }

    public LocalDate getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDate fechaSalida) { this.fechaSalida = fechaSalida; }

    public Long getCedula() { return cedula; }
    public void setCedula(Long cedula) { this.cedula = cedula; }

    public Integer getIdTipoHabitacion() { return idTipoHabitacion; }
    public void setIdTipoHabitacion(Integer idTipoHabitacion) { this.idTipoHabitacion = idTipoHabitacion; }
}
