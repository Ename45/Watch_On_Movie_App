package services;

import data.models.Movie;
import data.models.User;
import dto.requests.NewMovieDetailsRequest;
//import dto.requests.UserIdToCheckRequest;
import dto.responses.DeleteMovieResponse;
import dto.responses.MovieAddedToDatabaseResponse;

import java.util.List;

public interface AdminServices {

    MovieAddedToDatabaseResponse addMovieToDatabase(NewMovieDetailsRequest newMovieDetailsRequest);
    List<Movie> findAllMovies();
    Movie findMovieByName(String movieName);
    DeleteMovieResponse deleteMovieFromDatabaseById (String movieId);
}
