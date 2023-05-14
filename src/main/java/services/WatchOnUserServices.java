package services;

import com.sun.jdi.request.DuplicateRequestException;
import data.models.Movie;
import data.models.Role;
import data.models.User;
import data.repositories.MovieRepository;
import data.repositories.UserRepository;
import data.repositories.WatchOnMovieRepository;
import dto.requests.LoginRequest;
import dto.requests.SignUpRequest;
import dto.responses.LoginResponse;
import dto.responses.MovieAddedToUserListResponse;
import dto.responses.SignUpResponse;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WatchOnUserServices implements UserServices{

    UserRepository userRepository;
    MovieRepository movieRepository = new WatchOnMovieRepository();
    private static final String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!.@#&()–{}:;',?/*~$^+=<>])" +
            ".{5,20}$";
    private static final String emailRegex = "^(?=.{1,64}@)[\\p{L}0-9+_-]+(\\.[\\p{L}0-9+_-]+)*@"
            + "[^-][\\p{L}0-9+-]+(\\.[\\p{L}0-9+-]+)*(\\.\\p{L}{2,})$";
    static Pattern passwordPattern = Pattern.compile(passwordRegex);
    static Pattern emailPattern = Pattern.compile(emailRegex);

    public WatchOnUserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        String name = signUpRequest.getFullName();
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();

        emailValidator(email);
        passwordValidator(password);
        checkThatUserIsNotSignedUp(name, email);

        User user = setDetailsOfNewUser(name, email, password);
        userRepository.save(user);

        return getSignUpResponse();
    }


    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        User user = userRepository.findByEmail(email);

        throwExceptionIfNotSignedUp(user);

        verifyThatPasswordMatchesExistingUser(password, user);

        return getLoginResponse();
    }
    @Override
    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie findMovieByName(String movieName) {
        return movieRepository.findByName(movieName);
    }

    @Override
    public MovieAddedToUserListResponse saveMovieToUserList(String movieName) {
        return null;
    }

//    @Override
//    public String saveMovieToUserList(String movieName) {
//        User user = new User();
//        Movie movie = new Movie();
//
//        return null;
//    }
























    private static void emailValidator(String email) {
        Matcher matcher = emailPattern.matcher(email);
        if (!matcher.matches()){
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    private static void passwordValidator(String password) {
        Matcher matcher = passwordPattern.matcher(password);
        if (!matcher.matches()){
            throw new IllegalArgumentException("Password should have at least 1(capital letter, " +
                    "small letter, number, and a special character). Should be 5 characters or more.");
        }
    }

    private void checkThatUserIsNotSignedUp(String name, String email) {
        if (userRepository.findByName(name) != null){
            if (userRepository.findByEmail(email) != null) {
                throw new DuplicateRequestException("User already registered. Login instead");
            }
        }
    }

    private static User setDetailsOfNewUser(String name, String email, String password) {
        User user = new User();
        user.setFullName(name);
        user.setEmail(email);
        user.setPassword(password);
//        user.setRole(Role.valueOf(role.toUpperCase()));
        return user;
    }

    private static SignUpResponse getSignUpResponse() {
        SignUpResponse signUpResponse = new SignUpResponse();
        signUpResponse.setMessage("Sign Up successful. Check your email for a link to login");
        return signUpResponse;
    }


    private static void throwExceptionIfNotSignedUp(User user) {
        if (user == null){
            throw new IllegalArgumentException("Access denied: User is not signed up to this app");
        }
    }

    private static void verifyThatPasswordMatchesExistingUser(String password, User user) {
        if (!password.equals(user.getPassword())){
            throw new IllegalArgumentException("Invalid Password.");
        }
    }

    private static LoginResponse getLoginResponse() {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("Logged in.");
        return loginResponse;
    }


}
