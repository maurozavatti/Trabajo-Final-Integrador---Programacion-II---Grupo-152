package dao;

import entities.SeguroVehicular;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeguroVehicularDao implements GenericDao<SeguroVehicular, Integer> {

    @Override
    public SeguroVehicular create(Connection conn, SeguroVehicular s) throws SQLException {
        String sql = "INSERT INTO polizas (id_vehiculo_asegurado, fecha_inicio, fecha_fin, monto_total_asegurado) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, s.getIdVehiculoAsegurado());
            ps.setDate(2, Date.valueOf(s.getFechaInicio()));
            ps.setDate(3, Date.valueOf(s.getFechaFin()));
            if (s.getMontoTotalAsegurado() != null) {
                ps.setDouble(4, s.getMontoTotalAsegurado());
            } else {
                ps.setNull(4, Types.DOUBLE);
            }
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    s.setIdPoliza(rs.getInt(1));
                }
            }
            return s;
        }
    }

    @Override
    public Optional<SeguroVehicular> read(Connection conn, Integer idPoliza) throws SQLException {
        String sql = "SELECT id_poliza, id_vehiculo_asegurado, fecha_inicio, fecha_fin, monto_total_asegurado FROM polizas WHERE id_poliza = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPoliza);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SeguroVehicular s = mapRow(rs);
                    return Optional.of(s);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<SeguroVehicular> readAll(Connection conn) throws SQLException {
        String sql = "SELECT id_poliza, id_vehiculo_asegurado, fecha_inicio, fecha_fin, monto_total_asegurado FROM polizas ORDER BY id_poliza";
        List<SeguroVehicular> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    @Override
    public SeguroVehicular update(Connection conn, SeguroVehicular s) throws SQLException {
        String sql = "UPDATE polizas SET id_vehiculo_asegurado = ?, fecha_inicio = ?, fecha_fin = ?, monto_total_asegurado = ? WHERE id_poliza = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, s.getIdVehiculoAsegurado());
            ps.setDate(2, Date.valueOf(s.getFechaInicio()));
            ps.setDate(3, Date.valueOf(s.getFechaFin()));
            if (s.getMontoTotalAsegurado() != null) {
                ps.setDouble(4, s.getMontoTotalAsegurado());
            } else {
                ps.setNull(4, Types.DOUBLE);
            }
            ps.setInt(5, s.getIdPoliza());
            ps.executeUpdate();
            return s;
        }
    }

    @Override
    public boolean delete(Connection conn, Integer idPoliza) throws SQLException {
        String sql = "DELETE FROM polizas WHERE id_poliza = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPoliza);
            return ps.executeUpdate() > 0;
        }
    }

    public Optional<SeguroVehicular> findByVehiculo(Connection conn, Integer idVehiculoAsegurado) throws SQLException {
        String sql = "SELECT id_poliza, id_vehiculo_asegurado, fecha_inicio, fecha_fin, monto_total_asegurado FROM polizas WHERE id_vehiculo_asegurado = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idVehiculoAsegurado);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    private SeguroVehicular mapRow(ResultSet rs) throws SQLException {
        SeguroVehicular s = new SeguroVehicular();
        s.setIdPoliza(rs.getInt("id_poliza"));
        s.setIdVehiculoAsegurado(rs.getInt("id_vehiculo_asegurado"));
        s.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
        s.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
        double monto = rs.getDouble("monto_total_asegurado");
        if (rs.wasNull()) s.setMontoTotalAsegurado(null);
        else s.setMontoTotalAsegurado(monto);
        return s;
    }
}
