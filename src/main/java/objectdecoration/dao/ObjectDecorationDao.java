package objectdecoration.dao;

import objectdecoration.model.ObjectDecoration;

import java.util.List;
import java.util.Optional;

public interface ObjectDecorationDao {
    void save(ObjectDecoration object);
    Optional<ObjectDecoration> findById(int id);
    List<ObjectDecoration> findAll();
    boolean update(ObjectDecoration object);
    boolean delete(int id);
}
