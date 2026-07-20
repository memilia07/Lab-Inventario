package modelo;

import java.time.LocalDate;

public class Periferico extends Activo {

    private String tipoPeriferico;

    public Periferico(String codigo, String nombre, LocalDate fechaAdquisicion, EstadoActivo estado,
            String tipoPeriferico) {
        super(codigo, nombre, fechaAdquisicion, estado);
        this.tipoPeriferico = tipoPeriferico;
    }

    @Override
    public double calcularCostoMantenimiento() {
        double base = 15.0;
        if ("Impresora".equalsIgnoreCase(tipoPeriferico)) {
            return base + 35.0;
        }
        if ("Monitor".equalsIgnoreCase(tipoPeriferico)) {
            return base + 20.0;
        }
        return base;
    }

    @Override
    public String obtenerTipo() {
        return "PERIFERICO";
    }

    public String getTipoPeriferico() {
        return tipoPeriferico;
    }

    public void setTipoPeriferico(String tipoPeriferico) {
        this.tipoPeriferico = tipoPeriferico;
    }
}
