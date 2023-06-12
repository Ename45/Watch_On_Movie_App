package data.repositories;

import data.models.Role;
import data.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WatchOnUserRepositoryTest {

    User user;
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user = new User();
        userRepository = new WatchOnUserRepository();
    }

    @AfterEach
    public  void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Save a User")
    public void saveOneAdminTest() {
        assertEquals(0, userRepository.countUser());

        userRepository.save(user);
        assertEquals(1, userRepository.countUser());
    }

    @Test
    @DisplayName("Save two Users")
    public void saveTwoAdminTest() {
        userRepository.save(user);
        assertEquals(1, userRepository.countUser());

        User secondUser = new User();
        userRepository.save(secondUser);
        assertEquals(2, userRepository.countUser());
    }

    @Test
    @DisplayName("Can generate ID test for 1 User")
    public void saveOneAdmin_IdCountIsNotNull() {
        assertNull(user.getUserId());

        userRepository.save(user);
        assertEquals("1", user.getUserId());
    }

    @Test
    @DisplayName("Can generate ID test for 2 Users")
    public void saveTwoUsers_IdCountIsNotNull() {
        assertNull(user.getUserId());

        userRepository.save(user);
        assertEquals("1", user.getUserId());

        User user1 = new User();
        userRepository.save(user1);
        assertEquals("2", user1.getUserId());
    }

    @Test
    @DisplayName("Find User by ID")
    public void saveOneUser_findUserById_returnsSavedUserTest() {
        user.setFullName("Inem");
        userRepository.save(user);

        User foundUser = userRepository.findById("1");
        assertNotNull(foundUser.getUserId());
        assertEquals(user, foundUser);
        assertEquals(1, userRepository.countUser());
        assertEquals("1", user.getUserId());
        assertEquals("Inem", user.getFullName());
    }

    @Test
    @DisplayName("Find User by Email")
    public void saveOneUser_findUserByEmail_returnsSavedUserTest() {
        user.setFullName("Inem");
        user.setEmail("ename@gmail.com");
        userRepository.save(user);

        User foundUser = userRepository.findByEmail("ename@gmail.com");
        assertNotNull(foundUser.getEmail());
        assertEquals(user, foundUser);
        assertEquals(1, userRepository.countUser());
        assertEquals("1", user.getUserId());
        assertEquals("ename@gmail.com", user.getEmail());
    }

    @Test
    @DisplayName("Find User by Name")
    public void saveOneUser_findUserByName_returnsSavedUserTest() {
        user.setFullName("Inem");
        user.setEmail("ename@gmail.com");
        userRepository.save(user);

        User foundUser = userRepository.findByName("Inem");
        assertNotNull(foundUser.getFullName());
        assertEquals(user, foundUser);
        assertEquals(1, userRepository.countUser());
        assertEquals("1", user.getUserId());
        assertEquals("Inem", user.getFullName());
    }

    @Test
    @DisplayName("Update test")
    public void saveTwoUsersWithSameId_countIsOneTest(){
        user.setFullName("inem");
        userRepository.save(user);
        User savedOneUsersecondUser = userRepository.findById("1");
        assertEquals(user, savedOneUsersecondUser);


        User secondUser = new User();
        secondUser.setUserId("1");
        user.setFullName("Legends");

        userRepository.save(secondUser);

        User foundUser = userRepository.findById("1");

        assertEquals(foundUser, secondUser);
        assertEquals("1", user.getUserId());
        assertNotEquals(foundUser, user);
    }

    @Test
    @DisplayName("Count By Role")
    public void countUsersByRoleTest(){
        user.setFullName("inem");
        user.setRole(Role.USER);

        userRepository.save(user);
        int savedOneUser = userRepository.countByRole(Role.USER);
        assertEquals(1, savedOneUser);
        assertEquals(0, userRepository.countByRole(Role.ADMIN));


        User secondUser = new User();
        user.setFullName("Legends");
        user.setRole(Role.ADMIN);

        userRepository.save(secondUser);

        int savedOneAdmin = userRepository.countByRole(Role.ADMIN);

        assertEquals(1, savedOneAdmin);
        assertEquals(2, userRepository.findAll().size());
    }

    @Test
    @DisplayName("Delete test")
    public void deleteUserTest(){
        User user = new User();
        User user2 = new User();
        User user3 = new User();

        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);

        userRepository.deleteById(user2.getUserId());

        assertEquals("1", user.getUserId());
        assertEquals("2", user2.getUserId());
        assertEquals("3", user3.getUserId());
        assertEquals(2, userRepository.countUser());

        User user4 = new User();
        userRepository.save(user4);

        assertEquals(3, userRepository.countUser());
        assertEquals("4", user4.getUserId());
    }

    @Test
    @DisplayName("Find all Users")
    public void findAllAdminTest() {
        user.setFullName("Inem");
        userRepository.save(user);

        User user2 = new User();
        user2.setFullName("Joe");
        userRepository.save(user2);

        User user3 = new User();
        user3.setFullName("Tony");
        userRepository.save(user3);

        User user4 = new User();
        user4.setFullName("Legends");
        userRepository.save(user4);

        User user5 = new User();
        user5.setFullName("Ned");
        userRepository.save(user5);

        List<User> allUsers = userRepository.findAll();
        assertEquals(allUsers, userRepository.findAll());
    }
}