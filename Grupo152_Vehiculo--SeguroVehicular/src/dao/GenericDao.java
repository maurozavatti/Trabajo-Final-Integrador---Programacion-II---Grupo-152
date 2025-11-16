package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericDao<T, ID> {
    T create(Connection conn, T entity) throws SQLException;
    Optional<T> read(Connection conn, ID id) throws SQLException;
    List<T> readAll(Connection conn) throws SQLException;
    T update(Connection conn, T entity) throws SQLException;
    boolean delete(Connection conn, ID id) throws SQLException; // baja física o lógica según capa service
}
