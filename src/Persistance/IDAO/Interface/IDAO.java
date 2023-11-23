package Persistance.IDAO.Interface;

import Application.Model.Product;

import java.sql.SQLException;
import java.util.List;

public interface IDAO <T> {

    void save(T t) throws SQLException;

    void delete(Object id) throws SQLException;

    List<T> findAll() throws SQLException;

    T findById(Object id) throws SQLException;

    void update(T t) throws SQLException;
}
