package services;

import data.models.Movie;
import data.models.Role;
import data.models.User;
import dto.requests.LoginRequest;
import dto.requests.NewMovieDetailsRequest;
import dto.requests.SignUpRequest;
import dto.responses.*;

import java.util.List;

public interface UserServices {
    public SignUpResponse signUp (SignUpRequest signUpRequest);
    public LoginResponse login (LoginRequest loginRequest);
    List<Movie> findAllMovies();
    User findUsersByRole(Role role);
    User findUserById(String userId);
    Movie findMovieByName(String movieName);
    MovieAddedToUserListResponse saveMovieToUserList(String movieId);
    MovieSharedResponse shareMovie(String movieId);
    void deleteMovieFromUserList(String movieId);

//    STRICTLY ADMIN ROLES
//    MovieAddedToDatabaseResponse addMovieToDatabase(NewMovieDetailsRequest newMovieDetailsRequest);
//    DeleteMovieResponse deleteMovieFromDatabase (String movieId);
}
