package services;

import data.models.Movie;
import data.models.Role;
import data.models.User;
import data.repositories.MovieRepository;
import data.repositories.UserRepository;
import data.repositories.WatchOnMovieRepository;
import data.repositories.WatchOnUserRepository;
import dto.requests.NewMovieDetailsRequest;
import dto.responses.DeleteMovieResponse;
import dto.responses.MovieAddedToDatabaseResponse;

import java.time.LocalDateTime;
import java.util.List;
//@Service
public class WatchOnAdminServices implements AdminServices{
    MovieRepository movieRepository = new WatchOnMovieRepository();
    UserRepository userRepository = new WatchOnUserRepository();
//    UserServices userServices = new WatchOnUserServices(userRepository);

    public MovieAddedToDatabaseResponse addMovieToDatabase(NewMovieDetailsRequest newMovieDetailsRequest) {
//        User user = userRepository.findById(newMovieDetailsRequest.getUserId());

        MovieAddedToDatabaseResponse movieAddedToDatabaseResponse = null;
//        if (user != null && user.getRole() == Role.ADMIN) {
            String movieName = newMovieDetailsRequest.getMovieName();
            String genre = newMovieDetailsRequest.getGenre();
            String yearReleased = String.valueOf(newMovieDetailsRequest.getYear());
            String producer = newMovieDetailsRequest.getProducer();

            Movie newMovie = new Movie();
            newMovie.setMovieName(movieName);
            newMovie.setGenre(genre);
            newMovie.setYear(LocalDateTime.parse(yearReleased));
            newMovie.setProducer(producer);

            movieRepository.save(newMovie);

            movieAddedToDatabaseResponse = new MovieAddedToDatabaseResponse();
            movieAddedToDatabaseResponse.setMessage("Added to platform");
//        }
//        else {
//            movieAddedToDatabaseResponse = new MovieAddedToDatabaseResponse();
//            movieAddedToDatabaseResponse.setMessage("User is not an admin");
//        }

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

