package servicio;

import modelo.Activo;
import modelo.EstadoActivo;
import modelo.RegistroMantenimiento;
import repositorio.ActivoRepository;
import repositorio.MantenimientoRepository;
import java.time.LocalDate;
import java.util.List;

public class MantenimientoServiceImpl implements IMantenimientoService {

    private final MantenimientoRepository mantenimientoRepository;
    private final ActivoRepository activoRepository;

    public MantenimientoServiceImpl(MantenimientoRepository mantenimientoRepository,
                                     ActivoRepository activoRepository) {
        this.mantenimientoRepository = mantenimientoRepository;
        this.activoRepository = activoRepository;
    }

    @Override
    public RegistroMantenimiento registrarMantenimiento(String codigoActivo) {
        Activo activo = activoRepository.buscarPorCodigo(codigoActivo);
        if (activo == null) {
            throw new ActivoNoEncontradoException(codigoActivo);
        }
        double costo = activo.calcularCostoMantenimiento();
        RegistroMantenimiento registro = new RegistroMantenimiento(0, codigoActivo, LocalDate.now(), costo);
        RegistroMantenimiento guardado = mantenimientoRepository.guardar(registro);

        activo.setEstado(EstadoActivo.EN_MANTENIMIENTO);
        activoRepository.actualizar(activo);

        return guardado;
    }

    @Override
    public List<RegistroMantenimiento> historialDe(String codigoActivo) {
        if (activoRepository.buscarPorCodigo(codigoActivo) == null) {
            throw new ActivoNoEncontradoException(codigoActivo);
        }
        return mantenimientoRepository.listarPorActivo(codigoActivo);
    }

    @Override
    public double costoTotalMantenimiento() {
        return mantenimientoRepository.costoTotal();
    }
}