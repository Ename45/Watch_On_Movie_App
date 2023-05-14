package services;

import data.models.Movie;
import data.repositories.MovieRepository;
import data.repositories.WatchOnMovieRepository;
import dto.requests.AddNewMovieRequest;
import dto.responses.DeleteMovieResponse;

import java.time.LocalDateTime;
import java.util.List;

public class WatchOnAdminServices implements AdminServices{
    MovieRepository movieRepository = new WatchOnMovieRepository();
    @Override
    public Movie addMovie(AddNewMovieRequest addNewMovieRequest){
        String movieName = addNewMovieRequest.getMovieName();
        String movieGenre = addNewMovieRequest.getGenre();
        String movieYear = String.valueOf(addNewMovieRequest.getYear());
        String movieProducer = addNewMovieRequest.getProducer();

        Movie movie = new Movie();
        movie.setMovieName(movieName);
        movie.setGenre(movieGenre);
        movie.setYear(LocalDateTime.parse(movieYear));
        movie.setProducer(movieProducer);


        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie findMovieByName(String movieName) {
        return movieRepository.findByName(movieName);
    }

    @Override
    public DeleteMovieResponse deleteMovieByName(String movieName) {
        movieRepository.deleteByName(movieName);
        return null;
    }


}

