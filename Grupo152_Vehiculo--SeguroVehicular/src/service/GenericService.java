package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID> {
    T insertar(T entity) throws SQLException;
    Optional<T> getById(ID id) throws SQLException;
    List<T> getAll() throws SQLException;
    T actualizar(T entity) throws SQLException;
    boolean eliminar(ID id) throws SQLException; // baja física o lógica (aquí física por requerimiento)
}
