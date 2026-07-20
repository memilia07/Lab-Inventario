package repositorio;

import modelo.Activo;
import modelo.EstadoActivo;
import modelo.Hardware;
import modelo.Licencia;
import modelo.Periferico;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ActivoFactoryImpl implements ActivoFactory {

    @Override
    public Activo crearDesdeResultSet(ResultSet rs) throws SQLException {
        String codigo = rs.getString("codigo");
        String nombre = rs.getString("nombre");
        String tipo = rs.getString("tipo");
        LocalDate fechaAdquisicion = LocalDate.parse(rs.getString("fecha_adquision"));
        EstadoActivo estado = EstadoActivo.valueOf(rs.getString("estado"));

        switch (tipo) {
            case "HARDWARE":
                return new Hardware(codigo, nombre, fechaAdquisicion, estado,
                        rs.getInt("ram_gb"), rs.getString("procesador"));
            case "PERIFERICO":
                return new Periferico(codigo, nombre, fechaAdquisicion, estado,
                        rs.getString("tipo_periferico"));
            case "LICENCIA":
                String fechaExpStr = rs.getString("fecha_expiracion");
                LocalDate fechaExpiracion = fechaExpStr != null ? LocalDate.parse(fechaExpStr) : null;
                return new Licencia(codigo, nombre, fechaAdquisicion, estado, fechaExpiracion);
            default:
                throw new SQLException("Tipo de activo desconocido: " + tipo);
        }
    }
}
