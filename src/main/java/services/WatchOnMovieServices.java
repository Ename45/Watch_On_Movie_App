package services;

import data.models.Movie;
import data.repositories.MovieRepository;
import data.repositories.WatchOnMovieRepository;

import java.util.List;

public class WatchOnMovieServices implements MovieServices{
    private static final MovieRepository movieRepository = new WatchOnMovieRepository();


    @Override
    public Movie saveAMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie findMovieByName(String movieName) {
        return movieRepository.findByName(movieName);
    }

    @Override
    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public void deleteMovieById(String movieId) {
        movieRepository.deleteById(movieId);
    }
}
