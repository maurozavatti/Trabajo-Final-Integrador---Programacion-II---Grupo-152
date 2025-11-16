package service;

import config.DatabaseConnection;
import dao.SeguroVehicularDao;
import dao.VehiculoDao;
import entities.SeguroVehicular;
import entities.Vehiculo;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class VehiculoService implements GenericService<Vehiculo, Integer> {

    private final VehiculoDao vehiculoDao = new VehiculoDao();
    private final SeguroVehicularDao seguroDao = new SeguroVehicularDao();

    @Override
    public Vehiculo insertar(Vehiculo v) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(true);
            return vehiculoDao.create(conn, v);
        }
    }

    public void crearVehiculoConSeguro(Vehiculo v, SeguroVehicular s) throws SQLException {
        if (v.getDominio() == null || v.getDominio().isBlank()) {
            throw new IllegalArgumentException("El dominio es obligatorio.");
        }
        if (s.getFechaInicio() == null || s.getFechaFin() == null) {
            throw new IllegalArgumentException("Las fechas de la póliza son obligatorias.");
        }
        if (!s.getFechaFin().isAfter(s.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a la fecha de inicio.");
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);
                // 1) Crear vehículo
                Vehiculo creado = vehiculoDao.create(conn, v);
                // 2) Enlazar seguro al vehículo creado
                s.setIdVehiculoAsegurado(creado.getIdVehiculoAsegurado());
                // 3) Crear seguro
                seguroDao.create(conn, s);
                // 4) commit
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    @Override
    public Optional<Vehiculo> getById(Integer id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Optional<Vehiculo> v = vehiculoDao.read(conn, id);
            if (v.isPresent()) {
                seguroDao.findByVehiculo(conn, id).ifPresent(v.get()::setSeguro);
            }
            return v;
        }
    }

    @Override
    public List<Vehiculo> getAll() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            List<Vehiculo> list = vehiculoDao.readAll(conn);
            for (Vehiculo v : list) {
                seguroDao.findByVehiculo(conn, v.getIdVehiculoAsegurado()).ifPresent(v::setSeguro);
            }
            return list;
        }
    }

    @Override
    public Vehiculo actualizar(Vehiculo v) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(true);
            return vehiculoDao.update(conn, v);
        }
    }

    @Override
    public boolean eliminar(Integer id) throws SQLException {
        // Requerimiento: baja física en BD (sin columna eliminado). Primero eliminar la póliza si existe.
        try (Connection conn = DatabaseConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);
                seguroDao.findByVehiculo(conn, id).ifPresent(s -> {
                    try { seguroDao.delete(conn, s.getIdPoliza()); } catch (SQLException ex) { throw new RuntimeException(ex); }
                });
                boolean ok = vehiculoDao.delete(conn, id);
                conn.commit();
                return ok;
            } catch (Exception e) {
                conn.rollback();
                if (e instanceof SQLException) throw (SQLException)e;
                throw new SQLException("Error eliminando vehículo/póliza: " + e.getMessage(), e);
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}
