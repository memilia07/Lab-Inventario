package modelo;

<<<<<<< HEAD
import java.time.LocalDate;

=======

import java.time.LocalDate;

>>>>>>> 00302adf0df58179ffc159e02911ec0da21b6ac1
public class Hardware extends Activo {

    private int ramGb;
    private String procesador;

    public Hardware(String codigo, String nombre, LocalDate fechaAdquisicion, EstadoActivo estado,
            int ramGb, String procesador) {
        super(codigo, nombre, fechaAdquisicion, estado);
        this.ramGb = ramGb;
        this.procesador = procesador;
    }

    @Override
    public double calcularCostoMantenimiento() {
        double base = 50.0;
        double costoPorRam = ramGb * 5.0;
        return base + costoPorRam;
    }

    @Override
    public String obtenerTipo() {
        return "HARDWARE";
    }

    public int getRamGb() {
        return ramGb;
    }

    public void setRamGb(int ramGb) {
        this.ramGb = ramGb;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }
}
