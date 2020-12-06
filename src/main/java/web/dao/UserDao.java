package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {
    void create(User user);
    User read(long id);
    User readOne(String username);
    void update(long id, User user);
    void delete(User user);
    void deleteById(long id);
    List<User> listUsers();
}