import modelo.RegistroMantenimiento;
import servicio.IMantenimientoService;
 
import java.util.List;
 
public class MantenimientoController {
 
    private final IMantenimientoService service;
 
    public MantenimientoController(IMantenimientoService service) {
        this.service = service;
    }
 
    public RegistroMantenimiento registrarMantenimiento(String codigoActivo) {
        return service.registrarMantenimiento(codigoActivo);
    }
 
    public List<RegistroMantenimiento> historialDe(String codigoActivo) {
        return service.historialDe(codigoActivo);
    }
 
    public double costoTotal() {
        return service.costoTotalMantenimiento();
    }
}
