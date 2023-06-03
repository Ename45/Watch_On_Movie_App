package data.repositories;

//import data.models.Admin;
import data.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
//@Repository
public interface MovieRepository  {
    Movie save(Movie movie);
    Movie findById(String id);
    Movie findByName(String movieName);
    int countMovie();
    List<Movie> findAll();
    void deleteById(String id);
    void deleteByName(String movieName);
}
