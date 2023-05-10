package data.repositories;

import data.models.User;

import java.util.List;

public interface UserRepository {
    User save(User user);
    User findById(String id);
    User findByEmail(String email);
    User findByName(String name);
    int countUser();
    List<User> findAll();
    void deleteById(String id);
}
