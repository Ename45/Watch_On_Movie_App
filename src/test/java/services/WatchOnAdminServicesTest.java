package services;

import data.models.Admin;
import data.models.Movie;
import data.repositories.MovieRepository;
import data.repositories.UserRepository;
import data.repositories.WatchOnMovieRepository;
import data.repositories.WatchOnUserRepository;
import dto.requests.AddNewMovieRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class WatchOnAdminServicesTest {
    Admin admin;
    AdminServices adminServices;
    MovieRepository movieRepository;
    Movie movie;
    AddNewMovieRequest addNewMovieRequest;

    @BeforeEach
    void setUp() {
        admin = new Admin();
        adminServices = new WatchOnAdminServices();
        movie = new Movie();
        movieRepository = new WatchOnMovieRepository();
        addNewMovieRequest = new AddNewMovieRequest();
    }

    @Test
    @DisplayName("Only an Admin can save a Movie to the Database Test")
    public void anAdminCanAddAMovieTest(){
        addNewMovieRequest.setMovieName("Lord of the rings");
        addNewMovieRequest.setGenre("Fantasy");
        addNewMovieRequest.setYear(LocalDateTime.now().withYear(Integer.parseInt("2021")).withMonth(Integer.parseInt(
                "1")));
        addNewMovieRequest.setProducer("Inem Udousoro");


        Movie saveMovie = adminServices.addMovie(addNewMovieRequest);
        System.out.println(saveMovie.getMovieId());
        System.out.println(saveMovie.getYear());
        assertNotNull(saveMovie);

        assertEquals(1, adminServices.findAllMovies().size());
    }
}