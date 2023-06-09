package data.repositories;

//import data.models.Role;
import data.models.Role;
import data.models.User;

import java.util.ArrayList;
import java.util.List;

public class WatchOnUserRepository implements UserRepository{
    private static final List<User> userList = new ArrayList<>();
    private int idCount;

    @Override
    public User save(User user) {
        if (user.getUserId() != null){
            update(user);
        }
        else {
            saveNewUser(user);
        }
        return user;
    }

    private void update(User user) {
        User savedUser = findById(user.getUserId());
        int indexOfSavedUser = userList.indexOf(savedUser);
        userList.set(indexOfSavedUser, user);
    }

    private void saveNewUser(User user) {
        String id = generateId();
        user.setUserId(id);
        userList.add(user);
    }

    private String generateId() {
        return String.valueOf(idCount += 1);
    }


    @Override
    public User findById(String id) {

        for (User user: userList) {

            if (user.getUserId().equals(id)){
                return user;
            }
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        for (User user: userList) {
            if (user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }

    @Override
    public User findByName(String name) {
        User foundUser = null;
        for (User user: userList) {
            if (user.getFullName().equals(name)){
                foundUser = user;
            }
        }
        return foundUser;
    }

    @Override
    public User findByRole(Role role) {
        User foundRole = null;
        for (User user: userList) {
            if (user.getRole().equals(role)){
                foundRole = user;
            }
        }
        return foundRole;
    }

    @Override
    public int countUser() {
        return userList.size();
    }

    @Override
    public int countByRole(Role role) {
        int count = 0;
        for (User user : userList) {
            if (user.getRole() == role) {
                count++;
            }
        }
        return count;
    }

    @Override
    public List<User> findAll() {
        return userList;
    }

    @Override
    public void deleteById(String id) {
        User foundUserId = findById(id);
        userList.remove(foundUserId);
//        idCount--;
    }

    @Override
    public void deleteAll() {
        userList.clear();
    }
}
