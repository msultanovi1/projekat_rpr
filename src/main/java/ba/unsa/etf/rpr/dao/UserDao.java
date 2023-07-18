package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.MyBookListException;

import java.util.List;

public interface UserDao extends Dao<User>{
    List<User> searchByName(String name);

    User searchByNameAndPassword(String name, String password) throws MyBookListException;
}

