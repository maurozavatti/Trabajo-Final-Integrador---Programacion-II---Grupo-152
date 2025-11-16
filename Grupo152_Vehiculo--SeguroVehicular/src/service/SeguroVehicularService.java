package service;

import config.DatabaseConnection;
import dao.SeguroVehicularDao;
import entities.SeguroVehicular;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SeguroVehicularService implements GenericService<SeguroVehicular, Integer> {

    private final SeguroVehicularDao dao = new SeguroVehicularDao();

    @Override
    public SeguroVehicular insertar(SeguroVehicular s) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(true);
            return dao.create(conn, s);
        }
    }

    @Override
    public Optional<SeguroVehicular> getById(Integer id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return dao.read(conn, id);
        }
    }

    @Override
    public List<SeguroVehicular> getAll() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return dao.readAll(conn);
        }
    }

    @Override
    public SeguroVehicular actualizar(SeguroVehicular s) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(true);
            return dao.update(conn, s);
        }
    }

    @Override
    public boolean eliminar(Integer id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(true);
            return dao.delete(conn, id);
        }
    }
}
