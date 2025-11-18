package objectdecoration.service;

import objectdecoration.dao.ObjectDecorationDao;
import objectdecoration.model.ObjectDecoration;
import validators.ObjectDecorationValidator;

import java.util.List;
import java.util.Optional;

public class ObjectDecorationService {
    private final ObjectDecorationDao decorationDao;

    public ObjectDecorationService(ObjectDecorationDao decorationDao) {
        this.decorationDao = decorationDao;
    }

    public void createDecoration(ObjectDecoration decoration) {
        ObjectDecorationValidator.validate(decoration);
        decorationDao.save(decoration);
    }

    public Optional<ObjectDecoration> getDecorationById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than zero.");
        }
        return decorationDao.findById(id);
    }

    public List<ObjectDecoration> getAllDecorations() {
        return decorationDao.findAll();
    }

    public boolean updateDecoration(ObjectDecoration decoration) {
        ObjectDecorationValidator.validate(decoration);
        return decorationDao.update(decoration);
    }

    public boolean deleteDecoration(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID.");
        }
        return decorationDao.delete(id);
    }
}
