package objectdecoration.service;

import objectdecoration.dao.ObjectDecorationDao;
import objectdecoration.model.ObjectDecoration;

import java.util.List;
import java.util.Optional;

public class ObjectDecorationService {
    private final ObjectDecorationDao decorationDao;

    public ObjectDecorationService(ObjectDecorationDao decorationDao) {
        this.decorationDao = decorationDao;
    }

    public void createDecoration(ObjectDecoration decoration) {
        validate(decoration);
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
        validate(decoration);
        return decorationDao.update(decoration);
    }

    public boolean deleteDecoration(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID.");
        }
        return decorationDao.delete(id);
    }

    private void validate(ObjectDecoration decoration) {
        if (decoration == null) {
            throw new IllegalArgumentException("Decoration cannot be null.");
        }
        if (decoration.getName() == null || decoration.getName().isBlank()) {
            throw new IllegalArgumentException("Decoration name is required.");
        }
        if (decoration.getMaterial() == null || decoration.getMaterial().isBlank()) {
            throw new IllegalArgumentException("Material is required.");
        }
        if (decoration.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
    }
}
