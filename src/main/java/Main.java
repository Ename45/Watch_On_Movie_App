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
        displayMenu();
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
    static UserRepository userRepository = new WatchOnUserRepository();
    static UserServices userServices = new WatchOnUserServices(userRepository);
//    static Scanner scanner = new Scanner(System.in);

    private static void displayMenu() {
        String MainMenu = ("""
                Welcome to Watch_On. Have a blast with our plethora of movies
                Enter 1 -> SignUp
                      2 -> Login
                """);
        String userEntryChoice = input(MainMenu);
        while (!userEntryChoice.equals("1") && !userEntryChoice.equals("2")){
            display("Wrong entry. Pick from available options");
            userEntryChoice = input(MainMenu);
        }
        if (userEntryChoice.equals("1")){
            signUp();

        }
        else {
            login();
        }
    }

    private static void login(){
        String email = input("Enter your email");
        String password = input("Enter your password");

        User user = userRepository.findByEmail(email);
        if (user != null) {

            while (!password.equals(user.getPassword())){
                password = input("Password does not match. Enter your password");
            }

            loginRequest.setEmail(email);
            loginRequest.setPassword(password);
            loginResponse = userServices.login(loginRequest);
            display(loginResponse.getMessage());

        }
        else display("User does not exist");

        displayMenu();
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
