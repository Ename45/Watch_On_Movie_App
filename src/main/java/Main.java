import data.models.Role;
import data.models.User;
import data.repositories.UserRepository;
import data.repositories.WatchOnUserRepository;
import dto.requests.LoginRequest;
import dto.requests.SignUpRequest;
import dto.responses.LoginResponse;
import dto.responses.SignUpResponse;
import services.UserServices;
import services.WatchOnUserServices;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    public static void main(String[] args) {
//        displayMenu();
//        watchOnServiceMenu();
        System.out.println(Role.valueOf("admin"));
    }

    private static final String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!.@#&()–{}:;',?/*~$^+=<>])" +
            ".{5,20}$";
    private static final String emailRegex = "^(?=.{1,64}@)[\\p{L}0-9+_-]+(\\.[\\p{L}0-9+_-]+)*@" +
            "[^!.@#&()–{}:;',?/*~$^+=<>][\\p{L}0-9+-]+(\\.[\\p{L}0-9+-]+)*(\\.\\p{L}{2,})$";
    static Pattern passwordPattern = Pattern.compile(passwordRegex);
    static Pattern emailPattern = Pattern.compile(emailRegex);
    static SignUpRequest signUpRequest = new SignUpRequest();
    static SignUpResponse signUpResponse = new SignUpResponse();
    static LoginRequest loginRequest = new LoginRequest();
    static LoginResponse loginResponse = new LoginResponse();
//    static UserRepository userRepository = new WatchOnUserRepository();
    static UserServices userServices = WatchOnUserServices.getInstance();
//    static Scanner scanner = new Scanner(System.in);

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

    private static void watchOnServiceMenu() {
        String userServiceMenu = ("""
                What would you like to do?
                Enter 1 -> Find all movies
                      2 -> Find movie by name
                      3 -> add movie to my movie list
                      4 -> share movie
                """);
        String userChoice = input(userServiceMenu);
        switch (userChoice) {
            case "1" -> userServices.findAllMovies();

            case "2" -> findMovieByName();
//            case "3" -> userServices.saveMovieToUserList("");
//            case "4" -> userServices.shareMovie();
            default -> {
                errorMessage();
                watchOnServiceMenu();
            }
        }
    }




    private static void errorMessage() {
        display("Invalid input. Pick a number from those displayed in the menu");
    }

    private static void findMovieByName(){
        String movieName = input("Search for movie by name");
        if (movieName != null){
            userServices.findMovieByName(movieName);
        }
    }

    private static void login(){
        String email = input("Enter your email");
        String password = input("Enter your password");

//        User user = userServices.findByEmail(email);
        User user = null;
        if (user != null) {

            while (!password.equals(user.getPassword())){
                password = input("Password does not match. Enter your password");
            }

            loginRequest.setEmail(email);
            loginRequest.setPassword(password);
            loginResponse = userServices.login(loginRequest);
            if (loginResponse.getRole() == Role.ADMIN){
              String option = input("enter 1 for user\n enter 2 for admin ");
              switch (option){

                  case "1":
                      userPlatform();
//                  case "2": adminController.addMovieToDatabase("sgfhj");
              }
            }
            else if (loginResponse.getRole() == Role.USER){
                System.out.println(Role.USER);
            }
            display(loginResponse.getMessage());

        }
        else display("User does not exist");

//        displayMenu();
    }

    private static void userPlatform() {
        String option = input("enter 1 to save movie\n enter 2 to find movie");
        if (option.equals( "1")) {

//            userController.saveMovieToUserList("1");
        }
        if (option.equals("2")){
            String nameOfMovie = input("Enter the name of the movie ");
//            userController.findMovieByName(nameOfMovie);
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

        String password = input("Enter your 5-digit password: ");
        Matcher passwordMatcher = passwordPattern.matcher(password);
        while (!passwordMatcher.matches()){
            password = input("Password should contain at least one capital letter,\nsmall letter, number and a " +
                    "character.\nPassword should be more than 4 characters: ");
            passwordMatcher = passwordPattern.matcher(password);
        }
        signUpRequest.setPassword(password);
        user.setPassword(password);

        signUpResponse = userServices.signUp(signUpRequest);


        display(signUpResponse.getMessage());
        displayMenu();
    }

    private static void display(String message){
        JOptionPane.showMessageDialog(null, message);
    }

    private static String input(String prompt) {
        return JOptionPane.showInputDialog(prompt);
    }
}
