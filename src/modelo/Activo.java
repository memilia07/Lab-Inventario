package modelo;

import java.time.LocalDate;

public abstract class Activo {

    private String codigo;
    private String nombre;
    private LocalDate fechaAdquisicion;
    private EstadoActivo estado;

    public Activo(String codigo, String nombre, LocalDate fechaAdquisicion, EstadoActivo estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fechaAdquisicion = fechaAdquisicion;
        this.estado = estado;
    }

    public abstract double calcularCostoMantenimiento();

    // Identifica el tipo para persistencia. OCP: nuevos tipos solo implementan este metodo.
    public abstract String obtenerTipo();

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public EstadoActivo getEstado() {
        return estado;
    }

    public void setEstado(EstadoActivo estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s - %s - Costo Mant: %.2f",
                codigo, nombre, obtenerTipo(), estado, calcularCostoMantenimiento());
    }
}
