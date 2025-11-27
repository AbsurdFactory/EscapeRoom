package objectdecoration.service;

import exceptions.NotFoundException;
import objectdecoration.dao.ObjectDecorationDao;
import objectdecoration.model.ObjectDecoration;

import java.util.List;

public class ObjectDecorationService {
    private final ObjectDecorationDao dao;

    public ObjectDecorationService(ObjectDecorationDao dao) {
        this.dao = dao;
    }

    public void addObjectDecoration(String name, String material, double price) {
        ObjectDecoration object = ObjectDecoration.create(name, material, price);
        dao.save(object);
    }

    public ObjectDecoration getById(int id) {
        return dao.findById(id)
                .orElseThrow(() -> new NotFoundException("ObjectDecoration not found with id: " + id));
    }

    public List<ObjectDecoration> getAll() {
        return dao.findAll();
    }

    public boolean updateObjectDecoration(int id, String name, String material, double price) {
        ObjectDecoration updated = ObjectDecoration.rehydrate(id, name, material, price);
        return dao.update(updated);
    }

    public boolean deleteObjectDecoration(int id) {
        return dao.delete(id);
    }
}
