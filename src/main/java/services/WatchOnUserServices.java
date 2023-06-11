package services;

import com.sun.jdi.request.DuplicateRequestException;
import data.models.Movie;
import data.models.Role;
import data.models.User;
import data.repositories.*;
import dto.requests.LoginRequest;
import dto.requests.SignUpRequest;
import dto.responses.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WatchOnUserServices implements UserServices{
    private static final UserRepository userRepository = new WatchOnUserRepository();
    MovieServices movieServices = new WatchOnMovieServices();
    private static final String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!.@#&()–{}:;',?/*~$^+=<>])" +
            ".{5,20}$";
    private static final String emailRegex = "^(?=.{1,64}@)[\\p{L}0-9+_-]+(\\.[\\p{L}0-9+_-]+)*@"
            + "[^-][\\p{L}0-9+-]+(\\.[\\p{L}0-9+-]+)*(\\.\\p{L}{2,})$";

    private static final String companyPasswordRegex = "^wOc1\\d{2}2xxX$";

    static Pattern passwordPattern = Pattern.compile(passwordRegex);
    static Pattern emailPattern = Pattern.compile(emailRegex);
    static Pattern companyPasswordPattern = Pattern.compile(companyPasswordRegex);

    @Override
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        String name = signUpRequest.getFullName();
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();

        emailValidator(email);
        checkThatUserIsNotSignedUp(name, email);

        passwordValidator(password);

        User user = setDetailsOfNewUser(name, email, password);
        if (companyPasswordPattern.matcher(password).matches()) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }

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


        return getLoginResponse(user);
    }

    @Override
    public List<Movie> findAllMovies() {
        return movieServices.findAllMovies();
    }

    @Override
    public Movie findMovieByName(String movieName) {
        return movieServices.findMovieByName(movieName);
    }

    @Override
    public MovieAddedToUserListResponse addMovieToUserList(String movieName, User foundUser) {
        Movie movie = findMovieByName(movieName);
        String movieId = movie.getMovieId();

        if (movieId == null) {
            throw new IllegalArgumentException("Movie not found.");
        }

        foundUser.addMovieToList(movieId);
        userRepository.save(foundUser);

        MovieAddedToUserListResponse movieAddedToUserListResponse = new MovieAddedToUserListResponse();
        movieAddedToUserListResponse.setMessage("Movie added to your list.");

        return movieAddedToUserListResponse;
    }

    @Override
    public MovieSharedResponse shareMovie(String movieId, String senderId, String receiverId) {

        User sender = findUserById(senderId);
        User receiver = findUserById(receiverId);

//        sender.sendMovie(movieId);
//        receiver.receiveMovie(movieId);

        MovieSharedResponse movieSharedResponse = new MovieSharedResponse();
        movieSharedResponse.setMessage("sent movie to" + receiver.getFullName());

        return movieSharedResponse;
    }

    @Override
    public void deleteMovieFromUserListById(String movieId, User currentUser) {
        List <String> movieList = currentUser.getMovieId();

        for (String movie: movieList) {
//            if (movie.getMovieId() == movieId) movieList.remove(movieId);
        }
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

//    @Override
//    public User findUsersByRole(Role role) {
//        return userRepository.findByRole(role);
//    }

    @Override
    public User findUserById(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findByName(userName);
    }


    private static void emailValidator(String email) {
        Matcher matcher = emailPattern.matcher(email);
        if (!matcher.matches()){
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    private static void userPasswordValidator(String password) {
        Matcher matcher = passwordPattern.matcher(password);
        if (!matcher.matches()){
            throw new IllegalArgumentException("Password should have at least 1(capital letter, " +
                    "small letter, number, and a special character). Should be 5 characters or more.");
        }
    }

    private static void passwordValidator(String password) {
        Matcher matcher = companyPasswordPattern.matcher(password);
        if (!matcher.matches()){
            userPasswordValidator(password);
        }
    }

    private void checkThatUserIsNotSignedUp(String name, String email) {
        if (userRepository.findByName(name) != null && userRepository.findByEmail(email) != null){
                throw new DuplicateRequestException("User already registered. Login instead");
        }
    }

    private static User setDetailsOfNewUser(String name, String email, String password) {
        User user = new User();
        user.setFullName(name);
        user.setEmail(email);
        user.setPassword(password);
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

    private static LoginResponse getLoginResponse(User user) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("Logged in.");
        loginResponse.setEmail(user.getEmail());
        loginResponse.setFullName(user.getFullName());
        loginResponse.setMovieId(List.of("...movies"));
        loginResponse.setRole(user.getRole());
        loginResponse.setId(user.getUserId());
        return loginResponse;
    }
}
