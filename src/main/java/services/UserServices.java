package services;

import data.models.Movie;
import data.models.User;
import dto.requests.LoginRequest;
import dto.requests.SignUpRequest;
import dto.responses.*;

import java.util.List;

public interface UserServices {
    public SignUpResponse signUp(SignUpRequest signUpRequest);

    public LoginResponse login(LoginRequest loginRequest);

    List<Movie> findAllMovies();

    User findUserById(String userId);
    User findUserByUserName(String userName);

    Movie findMovieByName(String movieName);
    MovieAddedToUserListResponse addMovieToUserList(String movieName, User foundUser);

    MovieSharedResponse shareMovie(String movieId, String senderId, String receiverId);
    void deleteMovieFromUserListById(String movieId, User currentUser);

    User findByEmail(String email);

//    User findUsersByRole(Role role);

}
