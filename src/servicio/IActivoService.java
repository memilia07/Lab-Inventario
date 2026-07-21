package servicio;

import modelo.Activo;
import java.util.List;

public interface IActivoService {
    void registrarActivo(Activo activo);
    void actualizarActivo(Activo activo);
    List<Activo> listarActivos();
    Activo buscarActivo(String codigo);
    void asignarActivo(String codigo);
    void eliminarActivo(String codigo);
}