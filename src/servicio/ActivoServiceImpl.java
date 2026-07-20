package servicio;

import modelo.Activo;
import modelo.EstadoActivo;
import repositorio.ActivoRepository;

import java.util.List;

public class ActivoServiceImpl implements IActivoService {

    private final ActivoRepository repository;

    public ActivoServiceImpl(ActivoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registrarActivo(Activo activo) {
        validarActivo(activo);
        if (repository.existe(activo.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un activo con codigo: " + activo.getCodigo());
        }
        repository.guardar(activo);
    }

    @Override
    public void actualizarActivo(Activo activo) {
        validarActivo(activo);
        if (!repository.existe(activo.getCodigo())) {
            throw new ActivoNoEncontradoException(activo.getCodigo());
        }
        repository.actualizar(activo);
    }

    @Override
    public List<Activo> listarActivos() {
        return repository.listarTodos();
    }

    @Override
    public Activo buscarActivo(String codigo) {
        Activo activo = repository.buscarPorCodigo(codigo);
        if (activo == null) {
            throw new ActivoNoEncontradoException(codigo);
        }
        return activo;
    }

    @Override
    public void asignarActivo(String codigo) {
        Activo activo = buscarActivo(codigo);
        if (activo.getEstado() == EstadoActivo.ASIGNADO) {
            throw new IllegalStateException("El activo ya esta asignado");
        }
        activo.setEstado(EstadoActivo.ASIGNADO);
        repository.actualizar(activo);
    }

    @Override
    public void eliminarActivo(String codigo) {
        if (!repository.existe(codigo)) {
            throw new ActivoNoEncontradoException(codigo);
        }
        repository.eliminar(codigo);
    }

    private void validarActivo(Activo activo) {
        if (activo == null) {
            throw new IllegalArgumentException("El activo no puede ser nulo");
        }
        if (activo.getCodigo() == null || activo.getCodigo().isBlank()) {
            throw new IllegalArgumentException("El codigo es obligatorio");
        }
        if (activo.getNombre() == null || activo.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (activo.getFechaAdquisicion() == null) {
            throw new IllegalArgumentException("La fecha de adquisicion es obligatoria");
        }
    }
}
