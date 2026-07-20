package servicio;

public class ActivoNoEncontradoException extends RuntimeException {
    public ActivoNoEncontradoException(String codigo) {
        super("No existe un activo con codigo: " + codigo);
    }
}
