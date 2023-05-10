package data.repositories;

import data.models.Admin;

import java.util.List;

public interface AdminRepository{
    void save(Admin admin);
    Admin findById(String id);

    int countAdmin();
    List<Admin> findAll();
    void deleteById(String id);
}
