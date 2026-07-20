package modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Licencia extends Activo {

    private LocalDate fechaExpiracion;

    public Licencia(String codigo, String nombre, LocalDate fechaAdquisicion, EstadoActivo estado,
            LocalDate fechaExpiracion) {
        super(codigo, nombre, fechaAdquisicion, estado);
        this.fechaExpiracion = fechaExpiracion;
    }

    @Override
    public double calcularCostoMantenimiento() {
        long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), fechaExpiracion);
        if (diasRestantes < 0) {
            return 100.0;   // vencida: renovacion urgente
        }
        if (diasRestantes < 30) {
            return 60.0;   // proxima a vencer
        }
        return 25.0;                           // vigente
    }

    @Override
    public String obtenerTipo() {
        return "LICENCIA";
    }

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }
}
