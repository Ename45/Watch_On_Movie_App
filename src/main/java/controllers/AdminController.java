package controllers;

import data.models.Movie;
import dto.requests.NewMovieDetailsRequest;
import dto.requests.ShareMovieRequest;
import dto.responses.DeleteMovieResponse;
import dto.responses.MovieAddedToDatabaseResponse;
import dto.responses.MovieSharedResponse;
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

    //Todo who shares a movie? Just Admin?
    public MovieSharedResponse shareAMovie(ShareMovieRequest shareMovieRequest){
        return adminServices.shareAMovie(shareMovieRequest);
    }

    public DeleteMovieResponse deleteMovieFromDatabaseById(String movieId){
        return adminServices.deleteMovieFromDatabaseById(movieId);
    }
}
