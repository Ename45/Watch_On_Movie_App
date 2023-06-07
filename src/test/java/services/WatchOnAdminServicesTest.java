package services;

//import data.models.Admin;
import data.models.Movie;
import data.models.Role;
import data.models.User;
import data.repositories.MovieRepository;
import data.repositories.UserRepository;
import data.repositories.WatchOnMovieRepository;
import data.repositories.WatchOnUserRepository;
import dto.requests.LoginRequest;
import dto.requests.NewMovieDetailsRequest;
import dto.requests.SignUpRequest;
//import dto.requests.UserIdToCheckRequest;
import dto.responses.LoginResponse;
import dto.responses.MovieAddedToDatabaseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class WatchOnAdminServicesTest {
//    Admin admin;
    User user;
    AdminServices adminServices;
    UserServices userServices;
    SignUpRequest signUpRequest = new SignUpRequest();
    LoginRequest loginRequest = new LoginRequest();
    MovieRepository movieRepository;
    Movie movie;
    NewMovieDetailsRequest newMovieDetailsRequest;

    @BeforeEach
    void setUp() {
//        admin = new Admin();
        user = new User();
        adminServices = new WatchOnAdminServices();
//        UserRepository userRepository = new WatchOnUserRepository();
        userServices = WatchOnUserServices.getInstance();
        movie = new Movie();
        movieRepository = new WatchOnMovieRepository();
        newMovieDetailsRequest = new NewMovieDetailsRequest();
    }
        @Test
        @DisplayName("Only an Admin can save a Movie to the Database Test")
        public void anAdminCanAddAMovieTest() {
            signUpRequest.setFullName("Inemesit Udousoro");
            signUpRequest.setEmail("ename@gmail.com");
            signUpRequest.setPassword("wOc1472xxX");

            userServices.signUp(signUpRequest);

            loginRequest.setEmail("ename@gmail.com");
            loginRequest.setPassword("wOc1472xxX");
            LoginResponse loginResponse = userServices.login(loginRequest);

            NewMovieDetailsRequest newMovieDetailsRequest = new NewMovieDetailsRequest();

//            newMovieDetailsRequest.setRole(Role.ADMIN);
            newMovieDetailsRequest.setUserId(loginResponse.getId());

            MovieAddedToDatabaseResponse response = adminServices.addMovieToDatabase(newMovieDetailsRequest);

            assertEquals("Movie added to the database successfully", response.getMessage());
        }

    @Test
    @DisplayName("A User role cannot save Movie to the Database Test")
    public void anUserCannotAddAMovieTest() {
        signUpRequest.setFullName("Inemesit Udousoro");
        signUpRequest.setEmail("ename@gmail.com");
        signUpRequest.setPassword("In3m.");

        userServices.signUp(signUpRequest);

        loginRequest.setEmail("ename@gmail.com");
        loginRequest.setPassword("In3m.");
        LoginResponse loginResponse = userServices.login(loginRequest);
            NewMovieDetailsRequest newMovieDetailsRequest = new NewMovieDetailsRequest();

            newMovieDetailsRequest.setUserId(loginResponse.getId());

            MovieAddedToDatabaseResponse response = adminServices.addMovieToDatabase(newMovieDetailsRequest);

            assertEquals("User does not have admin privileges to add movies to the database", response.getMessage());
        }

//    @Test
//    @DisplayName("Only an Admin can delete movie from platform")
//    public void adminCanDeleteMovieFromDatabaseTest(){
//        signUpRequest.setFullName("Inemesit Udousoro");
//        signUpRequest.setEmail("ename@gmail.com");
//        signUpRequest.setPassword("wOc1472xxX");
//
//        userServices.signUp(signUpRequest);
//
//        loginRequest.setEmail("ename@gmail.com");
//        loginRequest.setPassword("wOc1472xxX");
//
//        userServices.login(loginRequest);
//
//        movie.setMovieName("Spy");
//        movie.setGenre("Action");
//        movie.setYear(LocalDateTime.parse("2023/05/20"));
//        movie.setProducer("John");
//
//        Movie savedMovie = movieRepository.save(movie);
//
//    }



}