package controllers;

import data.models.Movie;
import dto.requests.NewMovieDetailsRequest;
import dto.responses.DeleteMovieResponse;
import dto.responses.MovieAddedToDatabaseResponse;
import services.AdminServices;

import java.util.List;

public class AdminController {
    AdminServices adminServices;

    public List<Movie> findAllMovies() {
        return adminServices.findAllMovies();
    }

    public Movie findMovieByName(String movieName) {
        return adminServices.findMovieByName(movieName);
    }

    public MovieAddedToDatabaseResponse addMovieToDatabase(NewMovieDetailsRequest newMovieDetailsRequest){
        return adminServices.addMovieToDatabase(newMovieDetailsRequest);
    }

    public DeleteMovieResponse deleteMovieFromDatabaseById(String movieId){
        return adminServices.deleteMovieFromDatabaseById(movieId);
    }
}