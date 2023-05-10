import data.models.User;
import data.repositories.UserRepository;
import data.repositories.WatchOnUserRepository;
import dto.requests.SignUpRequest;
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

    private static final String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!.@#&()–[{}]:;',?/*~$^+=<>])" +
            ".{5,20}$";
    private static final String emailRegex = "^(?=.{1,64}@)[\\p{L}0-9+_-]+(\\.[\\p{L}0-9+_-]+)*@"
            + "[^-][\\p{L}0-9+-]+(\\.[\\p{L}0-9+-]+)*(\\.[\\p{L}]{2,})$";
    static Pattern passwordPattern = Pattern.compile(passwordRegex);
    static Pattern emailPattern = Pattern.compile(emailRegex);
    static SignUpRequest signUpRequest = new SignUpRequest();
    static SignUpResponse signUpResponse = new SignUpResponse();
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
        if (userEntryChoice.equals("1")){
            display("SIGN UP");
            signUp();

        }
        else if (userEntryChoice.equals("2")){
            display("LOGIN");
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
            email = input("Enter your Email: ");
            emailMatcher = emailPattern.matcher(email);
        }
        signUpRequest.setEmail(email);
        user.setEmail(email);

        String password = input("Enter your 5-digit password: ");
        Matcher passwordMatcher = passwordPattern.matcher(password);
        while (!passwordMatcher.matches()){
            password = input("Enter your 5-digit password: ");
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
