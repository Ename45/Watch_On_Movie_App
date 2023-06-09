package services;

import data.models.Movie;
import data.models.Role;
import data.models.User;
import data.repositories.MovieRepository;
import data.repositories.UserRepository;
import data.repositories.WatchOnMovieRepository;
import data.repositories.WatchOnUserRepository;
import dto.requests.NewMovieDetailsRequest;
//import dto.requests.UserIdToCheckRequest;
import dto.responses.DeleteMovieResponse;
import dto.responses.MovieAddedToDatabaseResponse;

import java.time.LocalDateTime;
import java.util.List;

public class WatchOnAdminServices implements AdminServices{
    private final MovieRepository movieRepository = new WatchOnMovieRepository();
    private final UserServices userServices = new WatchOnUserServices();


    public MovieAddedToDatabaseResponse addMovieToDatabase(NewMovieDetailsRequest newMovieDetailsRequest) {
        MovieAddedToDatabaseResponse movieAddedToDatabaseResponse;
//        Role personRole = newMovieDetailsRequest.getRole();
        String id = newMovieDetailsRequest.getUserId();

        User adminUser = userServices.findUserById(id);

        Role personRole = adminUser.getRole();

        if (personRole == Role.ADMIN) {
            String movieName = newMovieDetailsRequest.getMovieName();
            String genre = newMovieDetailsRequest.getGenre();
            LocalDateTime yearReleased = newMovieDetailsRequest.getYear();
            String producer = newMovieDetailsRequest.getProducer();

            Movie newMovie = new Movie();
            newMovie.setMovieName(movieName);
            newMovie.setGenre(genre);
            newMovie.setYear(yearReleased);
            newMovie.setProducer(producer);

            Movie savedMovie = movieRepository.save(newMovie);

            movieAddedToDatabaseResponse = new MovieAddedToDatabaseResponse();
            movieAddedToDatabaseResponse.setMessage("Movie added to the database successfully");
            movieAddedToDatabaseResponse.setMovieId(savedMovie.getMovieId());
        } else {
            movieAddedToDatabaseResponse = new MovieAddedToDatabaseResponse();
            movieAddedToDatabaseResponse.setMessage("User does not have admin privileges to add movies to the database");
        }

        return movieAddedToDatabaseResponse;
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
    public DeleteMovieResponse deleteMovieFromDatabaseById(String movieId) {
        movieRepository.deleteByName(movieId);
        DeleteMovieResponse deleteMovieResponse = new DeleteMovieResponse();
        deleteMovieResponse.setMessage("Movie deleted");
        return deleteMovieResponse;
    }


}

