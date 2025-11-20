package dao;

import objectdecoration.model.ObjectDecoration;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

    void save(T entity);

    Optional<T> findById(int id);

    List<T> findAll();

    boolean update(T entity);

    boolean delete(int id);
}
