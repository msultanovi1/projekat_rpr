package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.User;

import java.util.List;

public interface UserDao extends Dao<User>{
    List<User> searchByName(String name);

    List<User> searchByNameAndPassword(String name, String password);
}

