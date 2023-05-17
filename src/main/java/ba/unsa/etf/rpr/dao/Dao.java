package ba.unsa.etf.rpr.dao;

import java.util.List;

public interface Dao<Type> {

    Type getById(int id);

    Type add(Type item);

    Type update(Type item);

    void delete(int id);

    List<Type> getAll();

}
