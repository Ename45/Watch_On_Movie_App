package data.repositories;

import data.models.Movie;
import data.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WatchOnMovieRepositoryTest {
    Movie movie;
    MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movieRepository = new WatchOnMovieRepository();
    }

    @Test
    @DisplayName("Save a Movie")
    public void saveOneMovieTest() {
        assertEquals(0, movieRepository.countMovie());

        movieRepository.save(movie);
        assertEquals(1, movieRepository.countMovie());
    }

    @Test
    @DisplayName("Save two Movies")
    public void saveTwoMoviesTest() {
        movieRepository.save(movie);
        assertEquals(1, movieRepository.countMovie());

        Movie secondMovie = new Movie();
        movieRepository.save(secondMovie);
        assertEquals(2, movieRepository.countMovie());
    }

    @Test
    @DisplayName("Can generate ID test for 1 Movie")
    public void saveOneMovie_IdCountIsNotNull() {
        assertNull(movie.getMovieId());

        movieRepository.save(movie);
        assertEquals("1", movie.getMovieId());
    }

    @Test
    @DisplayName("Can generate ID test for 2 Movies")
    public void saveTwoMovies_IdCountIsNotNull() {
        assertNull(movie.getMovieId());

        movieRepository.save(movie);
        assertEquals("1", movie.getMovieId());

        Movie movie1 = new Movie();
        movieRepository.save(movie1);
        assertEquals("2", movie1.getMovieId());
    }

    @Test
    @DisplayName("Find Movie by ID")
    public void saveOneMovie_findMovieById_returnsSavedMovieTest() {
        movie.setMovieName("Lord of the rings");
        movieRepository.save(movie);

        Movie foundMovie = movieRepository.findById("1");
        assertNotNull(foundMovie.getMovieId());
        assertEquals(movie, foundMovie);
        assertEquals(1, movieRepository.countMovie());
        assertEquals("1", movie.getMovieId());
        assertEquals("Lord of the rings", movie.getMovieName());
    }

    @Test
    @DisplayName("Find Movie by name")
    public void saveOneMovie_findMovieByName_returnsSavedMovieTest() {
        movie.setMovieName("Lord of the rings");
        movieRepository.save(movie);

        Movie foundMovie = movieRepository.findByName(movie.getMovieName());
        assertNotNull(foundMovie.getMovieName());
        assertEquals(movie, foundMovie);
        assertEquals(1, movieRepository.countMovie());
        assertEquals("Lord of the rings", movie.getMovieName());
    }

    @Test
    @DisplayName("Update test")
    public void saveTwoMoviesWithSameId_countIsOneTest(){
        movie.setMovieName("Lord of the rings");
        movieRepository.save(movie);
        Movie savedOneMovie = movieRepository.findById("1");
        assertEquals(movie, savedOneMovie);


        Movie secondMovie = new Movie();
        secondMovie.setMovieId("1");
        movie.setMovieName("Arrow");

        movieRepository.save(secondMovie);

        Movie foundMovie = movieRepository.findById("1");

        assertEquals(foundMovie, secondMovie);
        assertEquals("1", movie.getMovieId());
        assertEquals("Arrow", movie.getMovieName());
        assertNotEquals(foundMovie, movie);
    }

    @Test
    @DisplayName("Delete movie by id test")
    public void deleteMovieByIdTest(){
        Movie movie = new Movie();
        Movie movie2 = new Movie();
        Movie movie3 = new Movie();

        movieRepository.save(movie);
        movieRepository.save(movie2);
        movieRepository.save(movie3);

        movieRepository.deleteById(movie2.getMovieId());

        assertEquals("1", movie.getMovieId());
        assertEquals("2", movie2.getMovieId());
        assertEquals("3", movie3.getMovieId());
        assertEquals(2, movieRepository.countMovie());

        Movie movie4 = new Movie();
        movieRepository.save(movie4);

        assertEquals(3, movieRepository.countMovie());
        assertEquals("3", movie4.getMovieId());
    }

    @Test
    @DisplayName("Delete by movie name test")
    public void deleteMovieByNameTest(){
        Movie movie = new Movie();
        Movie movie2 = new Movie();
        Movie movie3 = new Movie();

        movie.setMovieName("John Doe");
        movie2.setMovieName("Avatar");
        movie3.setMovieName("Arrows");

        movieRepository.save(movie);
        movieRepository.save(movie2);
        movieRepository.save(movie3);

        movieRepository.deleteByName(movie2.getMovieName());

        assertEquals(2, movieRepository.countMovie());

        Movie movie4 = new Movie();
        movie4.setMovieName("Bridgeton");
        movieRepository.save(movie4);

        assertEquals(3, movieRepository.countMovie());
        assertEquals("Bridgeton", movie4.getMovieName());
    }

    @Test
    @DisplayName("Find all Movies")
    public void findAllAdminTest() {
        movie.setMovieName("Lord of the rings");
        movieRepository.save(movie);

        Movie movie2 = new Movie();
        movie2.setMovieName("Avatar");
        movieRepository.save(movie2);

        Movie movie3 = new Movie();
        movie3.setMovieName("Arrow");
        movieRepository.save(movie3);

        Movie movie4 = new Movie();
        movie4.setMovieName("Avengers");
        movieRepository.save(movie4);

        Movie movie5 = new Movie();
        movie5.setMovieName("Bridgeton");
        movieRepository.save(movie5);

        List<Movie> allMovies = movieRepository.findAll();
        System.out.println(allMovies);
        assertEquals(allMovies, movieRepository.findAll());
    }
}