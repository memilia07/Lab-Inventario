package controlador;
import java.util.List;
import modelo.Activo;
import servicio.IActivoService;

public class ActivoController {
    private final IActivoService service;
 
    public ActivoController(IActivoService service) {
        this.service = service;
    }
 
    public void registrar(Activo activo) {
        service.registrarActivo(activo);
    }
 
    public List<Activo> listar() {
        return service.listarActivos();
    }
 
    public Activo buscar(String codigo) {
        return service.buscarActivo(codigo);
    }
 
    public void asignar(String codigo) {
        service.asignarActivo(codigo);
    }
 
    public void eliminar(String codigo) {
        service.eliminarActivo(codigo);
    }
}
