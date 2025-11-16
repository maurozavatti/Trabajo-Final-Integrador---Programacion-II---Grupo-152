package dao;

import entities.Vehiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehiculoDao implements GenericDao<Vehiculo, Integer> {

    @Override
    public Vehiculo create(Connection conn, Vehiculo v) throws SQLException {
        String sql = "INSERT INTO vehiculos_asegurados (dominio, id_auto) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, v.getDominio());
            ps.setInt(2, v.getIdAuto());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    v.setIdVehiculoAsegurado(rs.getInt(1));
                }
            }
            return v;
        }
    }

    @Override
    public Optional<Vehiculo> read(Connection conn, Integer id) throws SQLException {
        String sql = "SELECT id_vehiculo_asegurado, dominio, id_auto FROM vehiculos_asegurados WHERE id_vehiculo_asegurado = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Vehiculo v = new Vehiculo(
                        rs.getInt("id_vehiculo_asegurado"),
                        rs.getString("dominio"),
                        rs.getInt("id_auto")
                    );
                    return Optional.of(v);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Vehiculo> readAll(Connection conn) throws SQLException {
        String sql = "SELECT id_vehiculo_asegurado, dominio, id_auto FROM vehiculos_asegurados ORDER BY id_vehiculo_asegurado";
        List<Vehiculo> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Vehiculo v = new Vehiculo(
                    rs.getInt("id_vehiculo_asegurado"),
                    rs.getString("dominio"),
                    rs.getInt("id_auto")
                );
                list.add(v);
            }
        }
        return list;
    }

    @Override
    public Vehiculo update(Connection conn, Vehiculo v) throws SQLException {
        String sql = "UPDATE vehiculos_asegurados SET dominio = ?, id_auto = ? WHERE id_vehiculo_asegurado = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, v.getDominio());
            ps.setInt(2, v.getIdAuto());
            ps.setInt(3, v.getIdVehiculoAsegurado());
            ps.executeUpdate();
            return v;
        }
    }

    @Override
    public boolean delete(Connection conn, Integer id) throws SQLException {
        String sql = "DELETE FROM vehiculos_asegurados WHERE id_vehiculo_asegurado = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // Búsqueda por campo relevante (dominio)
    public Optional<Vehiculo> findByDominio(Connection conn, String dominio) throws SQLException {
        String sql = "SELECT id_vehiculo_asegurado, dominio, id_auto FROM vehiculos_asegurados WHERE UPPER(dominio) = UPPER(?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dominio);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Vehiculo v = new Vehiculo(
                        rs.getInt("id_vehiculo_asegurado"),
                        rs.getString("dominio"),
                        rs.getInt("id_auto")
                    );
                    return Optional.of(v);
                }
            }
        }
        return Optional.empty();
    }
}
