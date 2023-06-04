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
    public SignUpResponse signUp(SignUpRequest signUpRequest);

    public LoginResponse login(LoginRequest loginRequest);

    List<Movie> findAllMovies();

    User findUserById(String userId);

    Movie findMovieByName(String movieName);

    MovieAddedToUserListResponse saveMovieToUserList(String movieName, User foundUser);

    MovieSharedResponse shareMovie(String movieId, String senderId, String receiverId);

    void deleteMovieFromUserListById(String movieId);

//    void deleteMovieFromUserListByName(String movieName);

    User findUsersByRole(Role role);

}
