package objectdecoration.dao;

import objectdecoration.model.ObjectDecoration;

import java.util.List;
import java.util.Optional;

public class ObjectDecorationDaoImpl implements ObjectDecorationDao {
    @Override
    public void save(ObjectDecoration object) {

    }

    @Override
    public Optional<ObjectDecoration> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<ObjectDecoration> findAll() {
        return null;
    }

    @Override
    public boolean update(ObjectDecoration object) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
