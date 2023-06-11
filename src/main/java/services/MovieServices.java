package services;

import data.models.Movie;

import java.util.List;

public interface MovieServices {
    Movie saveAMovie(Movie Movie);
    Movie findMovieByName(String movieName);
    List<Movie> findAllMovies();
    void deleteMovieById(String movieId);
}
