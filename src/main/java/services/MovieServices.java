package services;

import data.models.Movie;

import java.util.List;

public interface MovieServices {
    Movie saveAMovie(Movie Movie);
    Movie findMovieByName(String movieName);
    List<Movie> findAllMovies();
    Movie findMovieById(String movieId);
    void deleteMovieById(String movieId);
}
