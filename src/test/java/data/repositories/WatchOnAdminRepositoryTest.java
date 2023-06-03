//package data.repositories;
//
//import data.models.Admin;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class WatchOnAdminRepositoryTest {
//
//    Admin admin;
//    AdminRepository adminRepository;
//
//    @BeforeEach
//    void setUp() {
//        admin = new Admin();
//        adminRepository = new WatchOnAdminRepository();
//    }
//
//    @Test
//    @DisplayName("Save an Admin")
//    public void saveOneAdminTest() {
//        assertEquals(0, adminRepository.countAdmin());
//
//        adminRepository.save(admin);
//        assertEquals(1, adminRepository.countAdmin());
//    }
//
//    @Test
//    @DisplayName("Save two Admins")
//    public void saveTwoAdminTest() {
//        adminRepository.save(admin);
//        assertEquals(1, adminRepository.countAdmin());
//
//        Admin secondAdmin = new Admin();
//        adminRepository.save(secondAdmin);
//        assertEquals(2, adminRepository.countAdmin());
//    }
//
//    @Test
//    @DisplayName("Can generate ID test for 1 Admin")
//    public void saveOneAdmin_IdCountIsNotNull() {
//        assertNull(admin.getAdminId());
//
//        adminRepository.save(admin);
//        assertEquals("1", admin.getAdminId());
//    }
//
//    @Test
//    @DisplayName("Can generate ID test for 2 Admins")
//    public void saveTwoAdmin_IdCountIsNotNull() {
//        assertNull(admin.getAdminId());
//
//        adminRepository.save(admin);
//        assertEquals("1", admin.getAdminId());
//
//        Admin admin1 = new Admin();
//        adminRepository.save(admin1);
//        assertEquals("2", admin1.getAdminId());
//    }
//
//    @Test
//    @DisplayName("Find Admin by ID")
//    public void saveOneAdmin_findAdminById_returnsSavedAdminTest() {
//        admin.setFullName("Inem");
//        adminRepository.save(admin);
//
//        Admin foundAdmin = adminRepository.findById("1");
//        assertNotNull(foundAdmin.getAdminId());
//        assertEquals(admin, foundAdmin);
//        assertEquals(1, adminRepository.countAdmin());
//        assertEquals("1", admin.getAdminId());
//        assertEquals("Inem", admin.getFullName());
//    }
//
//    @Test
//    @DisplayName("Update test")
//    public void saveTwoUsersWithSameId_countIsOneTest(){
//        admin.setFullName("inem");
////        var savedAUser = userRepository.save(user);
//        adminRepository.save(admin);
//        Admin savedOneAdmin = adminRepository.findById("1");
//        assertEquals(admin, savedOneAdmin);
//
//
//        Admin secondAdmin = new Admin();
//        secondAdmin.setAdminId("1");
//        admin.setFullName("Legends");
//
//        adminRepository.save(secondAdmin);
//
//        Admin foundAdmin = adminRepository.findById("1");
//
//        assertEquals(foundAdmin, secondAdmin);
//        assertEquals("1", admin.getAdminId());
//        assertNotEquals(foundAdmin, admin);
//    }
//
//    @Test
//    @DisplayName("Delete test")
//    public void deleteUserTest(){
//        Admin admin = new Admin();
//        Admin admin2 = new Admin();
//        Admin admin3 = new Admin();
//
//        adminRepository.save(admin);
//        adminRepository.save(admin2);
//        adminRepository.save(admin3);
//
//        adminRepository.deleteById(admin2.getAdminId());
//
//        assertEquals("1", admin.getAdminId());
//        assertEquals("2", admin2.getAdminId());
//        assertEquals("3", admin3.getAdminId());
//        assertEquals(2, adminRepository.countAdmin());
//
//        Admin admin4 = new Admin();
//        adminRepository.save(admin4);
//
//        assertEquals(3, adminRepository.countAdmin());
//        assertEquals("3", admin4.getAdminId());
//    }
//
//    @Test
//    @DisplayName("Find all Admins")
//    public void findAllAdminTest() {
//        admin.setFullName("Inem");
//        adminRepository.save(admin);
//
//        Admin admin2 = new Admin();
//        admin2.setFullName("Joe");
//        adminRepository.save(admin2);
//
//        Admin admin3 = new Admin();
//        admin3.setFullName("Tony");
//        adminRepository.save(admin3);
//
//        Admin admin4 = new Admin();
//        admin4.setFullName("Legends");
//        adminRepository.save(admin4);
//
//        Admin admin5 = new Admin();
//        admin5.setFullName("Ned");
//        adminRepository.save(admin5);
//
//        List<Admin> allAdmins = adminRepository.findAll();
//        System.out.println(allAdmins);
//        assertEquals(allAdmins, adminRepository.findAll());
//    }
//}