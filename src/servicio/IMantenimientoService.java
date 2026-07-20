package servicio;

import modelo.RegistroMantenimiento;
import java.util.List;

public interface IMantenimientoService {
    RegistroMantenimiento registrarMantenimiento(String codigoActivo);
    List<RegistroMantenimiento> historialDe(String codigoActivo);
    double costoTotalMantenimiento();
}