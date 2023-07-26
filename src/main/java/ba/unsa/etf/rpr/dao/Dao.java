package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.exceptions.MyBookListException;

import java.util.List;

public interface Dao<Type> {

    Type getById(int id) throws MyBookListException;

    Type add(Type item) throws MyBookListException;

    void update(Type item) throws MyBookListException;

    void delete(int id) throws MyBookListException;

    List<Type> getAll() throws MyBookListException;

}
