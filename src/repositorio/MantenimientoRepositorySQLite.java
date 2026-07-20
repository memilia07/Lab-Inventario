package repositorio;

import db.ConexionSQLite;
import modelo.RegistroMantenimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoRepositorySQLite implements MantenimientoRepository {

    public MantenimientoRepositorySQLite() {
        crearTablaSiNoExiste();
    }

    private void crearTablaSiNoExiste() {
        String sql = "CREATE TABLE IF NOT EXISTS mantenimientos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "codigo_activo TEXT," +
                "fecha TEXT NOT NULL," +
                "costo REAL NOT NULL," +
                "FOREIGN KEY(codigo_activo) REFERENCES activos(codigo))";
        try (Connection con = ConexionSQLite.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear tabla mantenimientos", e);
        }
    }

    @Override
    public RegistroMantenimiento guardar(RegistroMantenimiento registro) {
        String sql = "INSERT INTO mantenimientos (codigo_activo,fecha,costo) VALUES (?,?,?)";
        try (Connection con = ConexionSQLite.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, registro.getCodigoActivo());
            ps.setString(2, registro.getFecha().toString());
            ps.setDouble(3, registro.getCosto());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    registro.setId(keys.getInt(1));
                }
            }
            return registro;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar mantenimiento", e);
        }
    }

    @Override
    public List<RegistroMantenimiento> listarPorActivo(String codigoActivo) {
        List<RegistroMantenimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM mantenimientos WHERE codigo_activo=?";
        try (Connection con = ConexionSQLite.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigoActivo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new RegistroMantenimiento(
                            rs.getInt("id"),
                            rs.getString("codigo_activo"),
                            LocalDate.parse(rs.getString("fecha")),
                            rs.getDouble("costo")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar mantenimientos", e);
        }
        return lista;
    }

    @Override
    public double costoTotal() {
        String sql = "SELECT SUM(costo) AS total FROM mantenimientos";
        try (Connection con = ConexionSQLite.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al calcular costo total", e);
        }
        return 0.0;
    }
}