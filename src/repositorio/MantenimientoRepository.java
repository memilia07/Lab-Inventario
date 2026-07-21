package repositorio;

import modelo.RegistroMantenimiento;
import java.util.List;

public interface MantenimientoRepository {
    RegistroMantenimiento guardar(RegistroMantenimiento registro);
    List<RegistroMantenimiento> listarPorActivo(String codigoActivo);
    double costoTotal();
}