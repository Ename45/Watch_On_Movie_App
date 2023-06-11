package services;

import data.models.Movie;
import data.models.User;
import dto.requests.NewMovieDetailsRequest;
//import dto.requests.UserIdToCheckRequest;
import dto.requests.ShareMovieRequest;
import dto.responses.DeleteMovieResponse;
import dto.responses.MovieAddedToDatabaseResponse;
import dto.responses.MovieSharedResponse;

import java.util.List;

public interface AdminServices {
    MovieAddedToDatabaseResponse addMovieToDatabase(NewMovieDetailsRequest newMovieDetailsRequest);
//    User findUserById(String id);
    List<Movie> findAllMovies();
    Movie findMovieByName(String movieName);
    MovieSharedResponse shareAMovie(ShareMovieRequest shareMovieRequest);
    DeleteMovieResponse deleteMovieFromDatabaseById (String movieId);
}
