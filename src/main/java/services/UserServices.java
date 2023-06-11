package services;

import data.models.Movie;
import data.models.User;
import dto.requests.LoginRequest;
import dto.requests.ShareMovieRequest;
import dto.requests.SignUpRequest;
import dto.responses.*;

import java.util.List;

public interface UserServices {
    public SignUpResponse signUp(SignUpRequest signUpRequest);
    public LoginResponse login(LoginRequest loginRequest);
    List<Movie> findAllMovies();
    Movie findMovieByName(String movieName);
    User findUserById(String userId);
    User findUserByUserName(String userName);
    User findByEmail(String email);
    MovieAddedToUserListResponse addMovieToUserList(String movieName, User foundUser);
//    MovieSharedResponse share(ShareMovieRequest shareMovieRequest);

    DeleteFromUserListResponse deleteMovieFromUserListById(String movieId, User currentUser);

}
