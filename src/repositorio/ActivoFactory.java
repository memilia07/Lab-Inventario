package repositorio;

import modelo.Activo;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ActivoFactory {
    Activo crearDesdeResultSet(ResultSet rs) throws SQLException;
}