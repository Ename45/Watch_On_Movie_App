import controllers.AdminController;
import controllers.UserController;
import data.models.Role;
import data.models.User;
import dto.requests.LoginRequest;
import dto.requests.NewMovieDetailsRequest;
import dto.requests.ShareMovieRequest;
import dto.requests.SignUpRequest;
import dto.responses.LoginResponse;
import dto.responses.SignUpResponse;
import services.AdminServices;
import services.UserServices;
import services.WatchOnAdminServices;
import services.WatchOnUserServices;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    public static void main(String[] args) {
        displayMenu();
//        userPlatform();
    }



    private static final String userPasswordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!.@#&()–{}:;',?/*~$^+=<>])" +
            ".{5,20}$";
    private static final String emailRegex = "^(?=.{1,64}@)[\\p{L}0-9+_-]+(\\.[\\p{L}0-9+_-]+)*@" +
            "[^!.@#&()–{}:;',?/*~$^+=<>][\\p{L}0-9+-]+(\\.[\\p{L}0-9+-]+)*(\\.\\p{L}{2,})$";

    private static final String companyPasswordRegex = "^wOc1\\d{2}2xxX$";
    static Pattern userPasswordPattern = Pattern.compile(userPasswordRegex);
    static Pattern emailPattern = Pattern.compile(emailRegex);

    static Pattern companyPasswordPattern = Pattern.compile(companyPasswordRegex);
    static SignUpRequest signUpRequest = new SignUpRequest();
    static SignUpResponse signUpResponse = new SignUpResponse();
    static LoginRequest loginRequest = new LoginRequest();
    static LoginResponse loginResponse = new LoginResponse();
    static UserServices userServices = new WatchOnUserServices();

    static AdminServices adminServices = new WatchOnAdminServices();

    private static final UserController userController = new UserController();
    private static final AdminController adminController = new AdminController();

    static Scanner scanner = new Scanner(System.in);

    private static void displayMenu() {
        String mainMenu = ("""
                Welcome to Watch_On. Have a blast with our plethora of movies
                Enter 1 -> SignUp
                      2 -> Login
                """);
        String userEntryChoice = input(mainMenu);
        while (!userEntryChoice.equals("1") && !userEntryChoice.equals("2")){
            display("Wrong entry. Pick from available options");
            userEntryChoice = input(mainMenu);
        }
        if (userEntryChoice.equals("1")){
            signUp();

        }
        else {
            login();
        }
    }

    private static void userPlatform() {
        String userServiceMenu = ("""
                What would you like to do?
                Enter 1 -> Find all movies
                      2 -> Find movie by name
                      3 -> add movie to my movie list
                      4 -> delete movie from your list
                      5 -> share movie with friends
                      6 -> Find a friend by name
                      7 -> Find a friend by email
                """);
        String userChoice = input(userServiceMenu);
        switch (userChoice) {
            case "1" -> userController.findAllMovies();
            case "2" -> userController.findMovieByName(input("Search for movie by name"));
            case "3" -> userController.addMovieToUserList(input("Search for movie by name"), userController.findUserById(loginResponse.getId()));
//            case "4" -> userController.deleteMovieFromUserListById();
//            case "5" -> userController.shareMovie();
            case "6" -> userController.findUserByUserName(input("Search for friend by name"));
            case "7" -> userController.findUserByUserName(input("Search for friend by email"));
            default -> {
                errorMessage();
                userPlatform();
            }
        }
    }

    private static void adminPlatform() {
        String option = input("enter 1 add movie to database\n2 find movie by name\n3 find all movie" +
                "\n4 share movie\n5 delete movie from database");
        if (option.equals( "1")) {
            String movieName = input("Enter movie name ");
            String movieGenre = input("Enter movie genre ");
            String movieYear = input("Enter year released ");
            String movieProducer = input("Enter producers name ");
            NewMovieDetailsRequest newMovieDetailsRequest = new NewMovieDetailsRequest();
            newMovieDetailsRequest.setMovieName(movieName);
            newMovieDetailsRequest.setGenre(movieGenre);
            newMovieDetailsRequest.setYear(LocalDateTime.parse(movieYear));
            newMovieDetailsRequest.setProducer(movieProducer);
            adminController.addMovieToDatabase(newMovieDetailsRequest);
        }
        if (option.equals("2")){
            String movieName = input("Enter the name of the movie ");
            adminController.findMovieByName(movieName);
        }
        if (option.equals("3")){
            adminController.findAllMovies();
        }
        if (option.equals("4")){
//            String sender = input("Enter  ");
            String receiver = input("Enter receiver name or email");
            String movieToShare = input("Choose movie to share");
            ShareMovieRequest shareMovieRequest = new ShareMovieRequest();
            User foundUser = userServices.findUserById(shareMovieRequest.getSenderId());
            shareMovieRequest.setReceiverId(receiver);
            shareMovieRequest.setMovieId(movieToShare);
            adminController.shareAMovie(shareMovieRequest);
        }
        if (option.equals("5")){
            String movieToDelete = input("Enter the name of the movie ");
            adminController.deleteMovieFromDatabaseById(movieToDelete);
        }
    }

    private static void signUp() {
        User user = new User();

        String fullName = input("Enter your full name: ");
        while (fullName.isEmpty()){
            fullName = input("Enter your full name: ");
        }
        signUpRequest.setFullName(fullName);
        user.setFullName(fullName);


        String email = input("Enter your Email: ");
        Matcher emailMatcher = emailPattern.matcher(email);
        while (!emailMatcher.matches()){
            email = input("Wrong email Address. Enter your Email: ");
            emailMatcher = emailPattern.matcher(email);
        }
        signUpRequest.setEmail(email);
        user.setEmail(email);


        String password = input("Enter your password: ");
        Matcher companyMatcher = companyPasswordPattern.matcher(password);
        Matcher userPasswordMatcher = userPasswordPattern.matcher(password);
        while (true){
            if (userPasswordMatcher.matches() || companyMatcher.matches()){
                signUpRequest.setPassword(password);
                user.setPassword(password);
                break;
            }
            else{
                password = input("Invalid password format. Password should contain at least one capital letter," +
                        " small letter, number, and a special character. Password should be more than 4 characters.");
                companyMatcher = companyPasswordPattern.matcher(password);
                userPasswordMatcher = userPasswordPattern.matcher(password);
            }
        }

        signUpResponse = userServices.signUp(signUpRequest);

        display(signUpResponse.getMessage());
        displayMenu();
    }

    private static void login(){
        String email = input("Enter your email");
        String password = input("Enter your password");

        User user = userServices.findByEmail(email);
        if (user != null) {

            while (!password.equals(user.getPassword())){
                password = input("Password does not match. Enter your password");
            }

            loginRequest.setEmail(email);
            loginRequest.setPassword(password);
            loginResponse = userServices.login(loginRequest);

            display(loginResponse.getMessage());

            if (loginResponse.getRole() == Role.ADMIN){
              System.out.println(Role.USER);
              String option = input("enter 1 for user\n enter 2 for admin ");
              switch (option){

                  case "1":
                      userPlatform();
                  case "2":
                      adminPlatform();
              }
            }
            else if (loginResponse.getRole() == Role.USER){
                System.out.println(Role.USER);
                userPlatform();
            }
        }
        else display("User does not exist");
    }

    private static void findMovieByName(){
        String movieName = input("Search for movie by name");
        if (movieName != null){
            userServices.findMovieByName(movieName);
        }
    }

    private static void errorMessage() {
        display("Invalid input. Pick a number from those displayed in the menu");
    }

    private static void display(String message){
        JOptionPane.showMessageDialog(null, message);
    }

    private static String input(String prompt) {
        return JOptionPane.showInputDialog(prompt);
    }
}
