package data.repositories;

import data.models.Admin;
import data.models.Movie;

import java.util.List;

public interface MovieRepository {
    void save(Movie movie);
    Movie findById(String id);
    int countMovie();
    List<Movie> findAll();
    void deleteById(String id);
}
