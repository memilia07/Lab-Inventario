package modelo;

import java.time.LocalDate;

public class RegistroMantenimiento {

    private int id;
    private String codigoActivo;
    private LocalDate fecha;
    private double costo;

    public RegistroMantenimiento(int id, String codigoActivo, LocalDate fecha, double costo) {
        this.id = id;
        this.codigoActivo = codigoActivo;
        this.fecha = fecha;
        this.costo = costo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoActivo() {
        return codigoActivo;
    }

    public void setCodigoActivo(String codigoActivo) {
        this.codigoActivo = codigoActivo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return String.format("Mant. #%d - Activo: %s - Fecha: %s - Costo: %.2f",
                id, codigoActivo, fecha, costo);
    }
<<<<<<< HEAD
=======
    //.
>>>>>>> 00302adf0df58179ffc159e02911ec0da21b6ac1
}
