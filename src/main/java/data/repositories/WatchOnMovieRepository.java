package data.repositories;

import data.models.Admin;
import data.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class WatchOnMovieRepository implements MovieRepository{
    List<Movie> movieList = new ArrayList<>();
    private int idCount;

    @Override
    public void save(Movie movie) {
        if (movie.getMovieId() != null){
            update(movie);
        }
        else {
            saveNewMovie(movie);
        }
    }

    private void update(Movie movie) {
        Movie savedMovie = findById(movie.getMovieId());
        int indexOfSavedMovie = movieList.indexOf(savedMovie);
        movieList.set(indexOfSavedMovie, movie);
    }

    private void saveNewMovie(Movie movie) {
        String id = generateId();
        movie.setMovieId(id);
        movieList.add(movie);
    }

    private String generateId() {
        return String.valueOf(idCount += 1);
    }

    @Override
    public Movie findById(String id) {
        for (Movie movie: movieList) {
            if (movie.getMovieId().equals(id)){
                return movie;
            }
        }
        return null;
    }

    @Override
    public int countMovie() {
        return movieList.size();
    }

    @Override
    public List<Movie> findAll() {
        return movieList;
    }

    @Override
    public void deleteById(String id) {
        Movie foundMovieId = findById(id);
        movieList.remove(foundMovieId);
        idCount--;
    }
}
