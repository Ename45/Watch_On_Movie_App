package services;

import data.models.Movie;
import dto.requests.AddNewMovieRequest;
import dto.responses.DeleteMovieResponse;

import java.util.List;

public interface AdminServices {
    public Movie addMovie(AddNewMovieRequest addNewMovieRequest);
    public List<Movie> findAllMovies();
    public Movie findMovieByName(String movieName);
    public DeleteMovieResponse deleteMovieByName(String movieName);
}
