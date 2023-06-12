package controllers;

import data.models.Movie;
import data.models.User;
import dto.requests.LoginRequest;
import dto.requests.ShareMovieRequest;
import dto.requests.SignUpRequest;
import dto.responses.LoginResponse;
import dto.responses.MovieAddedToUserListResponse;
import dto.responses.MovieSharedResponse;
import dto.responses.SignUpResponse;
import services.UserServices;

import java.util.List;
public class UserController {
    //Todo: You did not use this controller at all
    UserServices userServices;
    public SignUpResponse signUp (SignUpRequest signUpRequest){
        return userServices.signUp(signUpRequest);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        return userServices.login(loginRequest);
    }

    public List<Movie> findAllMovies() {
        return userServices.findAllMovies();
    }

    public Movie findMovieByName(String movieName) {
        return userServices.findMovieByName(movieName);
    }

    public MovieAddedToUserListResponse addMovieToUserList(String movieName, User foundUser) {
        return userServices.addMovieToUserList(movieName, foundUser);
    }

//    public MovieSharedResponse shareMovie(ShareMovieRequest shareMovieRequest) {
//        return userServices.shareAMovie(shareMovieRequest);
//    }

    public void deleteMovieFromUserListById(String movieId, User currentUser) {
        userServices.deleteMovieFromUserListById(movieId, currentUser);
    }

    public User findByEmail(String email) {
        return userServices.findByEmail(email);
    }

    public User findUserById(String userId) {
        return userServices.findUserById(userId);
    }

    public User findUserByUserName(String userName) {
        return userServices.findUserByUserName(userName);
    }
}
