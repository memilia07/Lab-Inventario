
import modelo.Activo;
import java.util.List;

public interface ActivoRepository {

    void guardar(Activo activo);

    void actualizar(Activo activo);

    void eliminar(String codigo);

    Activo buscarPorCodigo(String codigo);

    List<Activo> listarTodos();

    boolean existe(String codigo);
}
