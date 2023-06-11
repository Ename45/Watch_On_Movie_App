package services;

import data.models.Movie;
import data.models.User;
import data.repositories.MovieRepository;
import data.repositories.UserRepository;
import data.repositories.WatchOnMovieRepository;
import data.repositories.WatchOnUserRepository;
import dto.requests.LoginRequest;
import dto.requests.NewMovieDetailsRequest;
import dto.requests.SignUpRequest;
import dto.responses.LoginResponse;
import dto.responses.MovieAddedToDatabaseResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class WatchOnAdminServicesTest {
    User user;
    AdminServices adminServices;
    UserServices userServices;
    SignUpRequest signUpRequest;
    LoginRequest loginRequest;
    LoginResponse loginResponse;
    MovieRepository movieRepository;
    Movie movie;
    NewMovieDetailsRequest newMovieDetailsRequest;
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user = new User();
        adminServices = new WatchOnAdminServices();
        signUpRequest = new SignUpRequest();
        loginRequest = new LoginRequest();
        loginResponse = new LoginResponse();
        userRepository = new WatchOnUserRepository();
        userServices = new WatchOnUserServices();
        movie = new Movie();
        movieRepository = new WatchOnMovieRepository();
        newMovieDetailsRequest = new NewMovieDetailsRequest();
    }

    @AfterEach
    void tearDown(){
        movieRepository.deleteAll();
        userRepository.deleteAll();
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

    @Test
    @DisplayName("Only an Admin can delete movie from platform")
    public void adminCanDeleteMovieFromDatabaseTest(){
        signUpRequest.setFullName("Inemesit Udousoro");
        signUpRequest.setEmail("ename@gmail.com");
        signUpRequest.setPassword("wOc1472xxX");

        userServices.signUp(signUpRequest);

        loginRequest.setEmail("ename@gmail.com");
        loginRequest.setPassword("wOc1472xxX");

        loginResponse = userServices.login(loginRequest);

        newMovieDetailsRequest.setUserId(loginResponse.getId());

        newMovieDetailsRequest.setMovieName("Spy");
        newMovieDetailsRequest.setGenre("Action");
        newMovieDetailsRequest.setYear(LocalDateTime.now().withYear(2023));
        newMovieDetailsRequest.setProducer("John Aisle");

        adminServices.addMovieToDatabase(newMovieDetailsRequest);

        Movie foundMovie = adminServices.findAllMovies().get(0);

        adminServices.deleteMovieFromDatabaseById(foundMovie.getMovieId());

        assertEquals(0, userServices.findAllMovies().size());

    }
}