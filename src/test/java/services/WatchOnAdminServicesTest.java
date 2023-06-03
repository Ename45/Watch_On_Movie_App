package services;

import data.models.Admin;
import data.models.Movie;
import data.models.Role;
import data.repositories.MovieRepository;
import data.repositories.UserRepository;
import data.repositories.WatchOnMovieRepository;
import data.repositories.WatchOnUserRepository;
import dto.requests.LoginRequest;
import dto.requests.NewMovieDetailsRequest;
import dto.requests.SignUpRequest;
import dto.responses.LoginResponse;
import dto.responses.MovieAddedToDatabaseResponse;
import dto.responses.SignUpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class WatchOnAdminServicesTest {
    Admin admin;
    AdminServices adminServices;
    UserServices userServices;
    SignUpRequest signUpRequest = new SignUpRequest();
    LoginRequest loginRequest = new LoginRequest();
    MovieRepository movieRepository;
    Movie movie;
    NewMovieDetailsRequest newMovieDetailsRequest;

    @BeforeEach
    void setUp() {
        admin = new Admin();
        adminServices = new WatchOnAdminServices();
        UserRepository userRepository = new WatchOnUserRepository();
        userServices = new WatchOnUserServices(userRepository);
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

        Role role = loginResponse.getRole();
        System.out.println(role);

        MovieAddedToDatabaseResponse savedMovie = null;
        if (role == Role.ADMIN) {
            newMovieDetailsRequest.setMovieName("Lord of the rings");
            newMovieDetailsRequest.setGenre("Fantasy");
//            newMovieDetailsRequest.setUserId("1");
            newMovieDetailsRequest.setYear(LocalDateTime.now().withYear(Integer.parseInt("2021")).withMonth(Integer.parseInt(
                    "1")));
            newMovieDetailsRequest.setProducer("Inem Udousoro");


        savedMovie = adminServices.addMovieToDatabase(newMovieDetailsRequest);
        }
        assertNotNull(savedMovie);

        assertEquals(1, adminServices.findAllMovies().size());
    }


}