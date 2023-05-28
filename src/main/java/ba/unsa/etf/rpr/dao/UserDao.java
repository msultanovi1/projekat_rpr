package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.User;

import java.util.List;

public interface UserDao {
    List<User> searchByName(String name);
}

