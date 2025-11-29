package objectdecoration.service;

import commonValueObjects.Id;
import exceptions.NotFoundException;
import objectdecoration.dao.ObjectDecorationDao;
import objectdecoration.model.ObjectDecoration;

import java.util.List;

public class ObjectDecorationService {
    private final ObjectDecorationDao dao;

    public ObjectDecorationService(ObjectDecorationDao dao) {
        this.dao = dao;
    }

    public void addObjectDecoration(ObjectDecoration objectDecoration){
        dao.save(objectDecoration);
    }

    public void addObjectDecoration(String name, String material, double price) {
        ObjectDecoration object = ObjectDecoration.create(name, material, price);
        dao.save(object);
    }

    public ObjectDecoration getById(Id id) {
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

    public boolean deleteObjectDecoration(Id id) {
        return dao.delete(id);
    }
}
