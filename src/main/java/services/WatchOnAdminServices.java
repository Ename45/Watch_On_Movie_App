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
    MovieRepository movieRepository = new WatchOnMovieRepository();
    UserRepository userRepository = new WatchOnUserRepository();
    UserServices userServices = new WatchOnUserServices(userRepository);

//    public MovieAddedToDatabaseResponse addMovieToDatabase(NewMovieDetailsRequest newMovieDetailsRequest,
//                                                           UserIdToCheckRequest userIdToCheckRequest) {
////        user = userRepository.findById(newMovieDetailsRequest.getUserId());
////        user = userServices.findUserById(newMovieDetailsRequest.getUserId());
//        MovieAddedToDatabaseResponse movieAddedToDatabaseResponse = null;
//
//            String movieName = newMovieDetailsRequest.getMovieName();
//            String genre = newMovieDetailsRequest.getGenre();
//            String yearReleased = String.valueOf(newMovieDetailsRequest.getYear());
//            String producer = newMovieDetailsRequest.getProducer();
//
//            Movie newMovie = new Movie();
//            newMovie.setMovieName(movieName);
//            newMovie.setGenre(genre);
//            newMovie.setYear(LocalDateTime.parse(yearReleased));
//            newMovie.setProducer(producer);
//
//            String adminId = userIdToCheckRequest.getUserId();
//            User user = userServices.findUserById(adminId);
////        System.out.println(user.getRole());
////        System.out.println(user);
//
//        if (user != null && user.getRole() == Role.ADMIN) {
//            movieRepository.save(newMovie);
//
//            movieAddedToDatabaseResponse = new MovieAddedToDatabaseResponse();
//            movieAddedToDatabaseResponse.setMessage("Added to platform");
//        }
////        else {
////            movieAddedToDatabaseResponse = new MovieAddedToDatabaseResponse();
////            movieAddedToDatabaseResponse.setMessage("User is not an admin");
////        }
//
//        return movieAddedToDatabaseResponse;
//    }

    public MovieAddedToDatabaseResponse addMovieToDatabase(NewMovieDetailsRequest newMovieDetailsRequest) {
        MovieAddedToDatabaseResponse movieAddedToDatabaseResponse;
        Role personRole = newMovieDetailsRequest.getRole();

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

