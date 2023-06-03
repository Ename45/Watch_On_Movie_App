//package data.repositories;
//
//import data.models.Admin;
//import data.models.User;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class WatchOnAdminRepository implements AdminRepository{
//    List<Admin> adminList = new ArrayList<>();
//    private int idCount;
//
//    @Override
//    public void save(Admin admin) {
//        if (admin.getAdminId() != null){
//            update(admin);
//        }
//        else {
//            saveNewAdmin(admin);
//        }
//    }
//
//    private void update(Admin admin) {
//        Admin savedAdmin = findById(admin.getAdminId());
//        int indexOfSavedAdmin = adminList.indexOf(savedAdmin);
//        adminList.set(indexOfSavedAdmin, admin);
//    }
//
//    private void saveNewAdmin(Admin admin) {
//        String id = generateId();
//        admin.setAdminId(id);
//        adminList.add(admin);
//    }
//
//    private String generateId() {
//        return String.valueOf(idCount += 1);
//    }
//
//    @Override
//    public Admin findById(String id) {
//        for (Admin admin: adminList) {
//            if (admin.getAdminId().equals(id)){
//                return admin;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public int countAdmin() {
//        return adminList.size();
//    }
//
//    @Override
//    public List<Admin> findAll() {
//        return adminList;
//    }
//
//    @Override
//    public void deleteById(String id) {
//        Admin foundAdminId = findById(id);
//        adminList.remove(foundAdminId);
//        idCount--;
//    }
//}
