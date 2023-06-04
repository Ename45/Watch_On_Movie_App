package data.repositories;

import data.models.Role;
import data.models.User;

import java.util.List;

public interface UserRepository {
    User save(User user);
    User findById(String id);
    User findByEmail(String email);
    User findByName(String name);
    User findByRole(Role role);
    int countUser();
    int countByRole(Role role);
    List<User> findAll();
    void deleteById(String id);
}
