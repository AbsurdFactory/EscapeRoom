package dao;

import commonValueObjects.Id;
import commonValueObjects.Name;
import objectdecoration.model.ObjectDecoration;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

    void save(T entity);

    Optional<T> findById(Id id);

    List<T> findAll();

    boolean update(T entity);

    boolean delete(Id id);

    boolean deleteByName(Name name);
}
