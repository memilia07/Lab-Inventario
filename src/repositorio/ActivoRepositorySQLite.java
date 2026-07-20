package repositorio;

import db.ConexionSQLite;
import modelo.Activo;
import modelo.Hardware;
import modelo.Licencia;
import modelo.Periferico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivoRepositorySQLite implements ActivoRepository {

    private final ActivoFactory factory;

    public ActivoRepositorySQLite() {
        this.factory = new ActivoFactoryImpl();
        crearTablaSiNoExiste();
    }

    private void crearTablaSiNoExiste() {
        String sql = "CREATE TABLE IF NOT EXISTS activos (" +
                "codigo TEXT PRIMARY KEY," +
                "nombre TEXT NOT NULL," +
                "tipo TEXT NOT NULL," +
                "fecha_adquision TEXT NOT NULL," +
                "estado TEXT NOT NULL," +
                "ram_gb INTEGER," +
                "procesador NUMERIC," +
                "tipo_periferico TEXT," +
                "fecha_expiracion TEXT)";
        try (Connection con = ConexionSQLite.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear tabla activos", e);
        }
    }

    @Override
    public void guardar(Activo activo) {
        String sql = "INSERT INTO activos (codigo,nombre,tipo,fecha_adquision,estado," +
                "ram_gb,procesador,tipo_periferico,fecha_expiracion) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection con = ConexionSQLite.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            mapearParametros(ps, activo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar activo", e);
        }
    }

    @Override
    public void actualizar(Activo activo) {
        String sql = "UPDATE activos SET nombre=?,tipo=?,fecha_adquision=?,estado=?," +
                "ram_gb=?,procesador=?,tipo_periferico=?,fecha_expiracion=? WHERE codigo=?";
        try (Connection con = ConexionSQLite.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, activo.getNombre());
            ps.setString(2, activo.obtenerTipo());
            ps.setString(3, activo.getFechaAdquisicion().toString());
            ps.setString(4, activo.getEstado().name());
            setCamposEspecificos(ps, activo, 5);
            ps.setString(9, activo.getCodigo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar activo", e);
        }
    }

    @Override
    public void eliminar(String codigo) {
        String sql = "DELETE FROM activos WHERE codigo=?";
        try (Connection con = ConexionSQLite.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar activo", e);
        }
    }

    @Override
    public Activo buscarPorCodigo(String codigo) {
        String sql = "SELECT * FROM activos WHERE codigo=?";
        try (Connection con = ConexionSQLite.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return factory.crearDesdeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar activo", e);
        }
        return null;
    }

    @Override
    public List<Activo> listarTodos() {
        List<Activo> lista = new ArrayList<>();
        String sql = "SELECT * FROM activos";
        try (Connection con = ConexionSQLite.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(factory.crearDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar activos", e);
        }
        return lista;
    }

    @Override
    public boolean existe(String codigo) {
        String sql = "SELECT 1 FROM activos WHERE codigo=?";
        try (Connection con = ConexionSQLite.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de activo", e);
        }
    }

    private void mapearParametros(PreparedStatement ps, Activo activo) throws SQLException {
        ps.setString(1, activo.getCodigo());
        ps.setString(2, activo.getNombre());
        ps.setString(3, activo.obtenerTipo());
        ps.setString(4, activo.getFechaAdquisicion().toString());
        ps.setString(5, activo.getEstado().name());
        setCamposEspecificos(ps, activo, 6);
    }

    // indiceInicial: posicion de ram_gb en el PreparedStatement
    private void setCamposEspecificos(PreparedStatement ps, Activo activo, int indiceInicial) throws SQLException {
        if (activo instanceof Hardware hw) {
            ps.setInt(indiceInicial, hw.getRamGb());
            ps.setString(indiceInicial + 1, hw.getProcesador());
            ps.setNull(indiceInicial + 2, java.sql.Types.VARCHAR);
            ps.setNull(indiceInicial + 3, java.sql.Types.VARCHAR);
        } else if (activo instanceof Periferico per) {
            ps.setNull(indiceInicial, java.sql.Types.INTEGER);
            ps.setNull(indiceInicial + 1, java.sql.Types.VARCHAR);
            ps.setString(indiceInicial + 2, per.getTipoPeriferico());
            ps.setNull(indiceInicial + 3, java.sql.Types.VARCHAR);
        } else if (activo instanceof Licencia lic) {
            ps.setNull(indiceInicial, java.sql.Types.INTEGER);
            ps.setNull(indiceInicial + 1, java.sql.Types.VARCHAR);
            ps.setNull(indiceInicial + 2, java.sql.Types.VARCHAR);
            ps.setString(indiceInicial + 3, lic.getFechaExpiracion() != null ? lic.getFechaExpiracion().toString() : null);
        }
    }
}