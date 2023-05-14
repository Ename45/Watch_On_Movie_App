package services;

import data.models.Movie;
import dto.requests.LoginRequest;
import dto.requests.SignUpRequest;
import dto.responses.LoginResponse;
import dto.responses.MovieAddedToUserListResponse;
import dto.responses.SignUpResponse;

import java.util.List;

public interface UserServices {
    public SignUpResponse signUp (SignUpRequest signUpRequest);
    public LoginResponse login (LoginRequest loginRequest);
    List<Movie> findAllMovies();
    Movie findMovieByName(String movieName);
    public MovieAddedToUserListResponse saveMovieToUserList(String movieName);
//    public MovieSharedResponse shareMovie(Movie movieId);
}
