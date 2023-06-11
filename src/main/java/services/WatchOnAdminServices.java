package services;

import data.models.Movie;
import data.models.Role;
import data.models.SharedItems;
import data.models.User;
import dto.requests.NewMovieDetailsRequest;
import dto.requests.ShareMovieRequest;
import dto.responses.DeleteMovieResponse;
import dto.responses.MovieAddedToDatabaseResponse;
import dto.responses.MovieSharedResponse;

import java.time.LocalDateTime;
import java.util.List;

public class WatchOnAdminServices implements AdminServices{

    UserServices userServices = new WatchOnUserServices();
    MovieServices movieServices = new WatchOnMovieServices();


    public MovieAddedToDatabaseResponse addMovieToDatabase(NewMovieDetailsRequest newMovieDetailsRequest) {
        MovieAddedToDatabaseResponse movieAddedToDatabaseResponse;
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

            Movie savedMovie = movieServices.saveAMovie(newMovie);

            movieAddedToDatabaseResponse = new MovieAddedToDatabaseResponse();
            movieAddedToDatabaseResponse.setMessage("Movie added to the database successfully");
            movieAddedToDatabaseResponse.setMovieId(savedMovie.getMovieId());
        } else {
            movieAddedToDatabaseResponse = new MovieAddedToDatabaseResponse();
            movieAddedToDatabaseResponse.setMessage("User does not have admin privileges to add movies to the database");
        }

        return movieAddedToDatabaseResponse;
    }

//
//    @Override
//    public User findUserById(String id) {
//        return userServices.findUserById(id);
//    }


    @Override
    public List<Movie> findAllMovies() {
        return movieServices.findAllMovies();
    }

    @Override
    public Movie findMovieByName(String movieName) {
        return movieServices.findMovieByName(movieName);
    }

    @Override
    public MovieSharedResponse shareAMovie(ShareMovieRequest shareMovieRequest) {
        User sender = userServices.findUserById(shareMovieRequest.getSenderId());
        User receiver = userServices.findUserById(shareMovieRequest.getReceiverId());
        Movie movieToShare = movieServices.findMovieById(shareMovieRequest.getMovieId());


        if (sender == null || receiver == null) {
            throw new IllegalArgumentException("User not found on the platform. Invite friend to signup.");
        }

        SharedItems sharedItems = new SharedItems();
        sharedItems.getSenderId();
        sharedItems.setMovieId(movieToShare);
        receiver.receiveSharedItem(sharedItems);

        MovieSharedResponse movieSharedResponse = new MovieSharedResponse();
        movieSharedResponse.setMessage("sent movie to" + receiver.getFullName());

        return movieSharedResponse;
    }

    @Override
    public DeleteMovieResponse deleteMovieFromDatabaseById(String movieId) {
        movieServices.deleteMovieById(movieId);
        DeleteMovieResponse deleteMovieResponse = new DeleteMovieResponse();
        deleteMovieResponse.setMessage("Movie deleted");
        return deleteMovieResponse;
    }


}

