package data.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class User {
    private String userId;
    private String fullName;
    private String email;
    private String password;
    private List<String> movieId = new ArrayList<>();
    private List<SharedItems> sharedMovies = new ArrayList<>();
    private Role role;

    public void addMovieToList(String movieId){
        this.movieId.add(movieId);
    }

    public void removeMovieFromList(String movieId){
        this.movieId.remove(movieId);
    }

    public void receiveSharedItem(SharedItems sharedItems){
        sharedItems.getSenderId();
        sharedItems.getMovieId();
        this.sharedMovies.add(sharedItems);
    }

    public void removeMovieFromSharedList(SharedItems sharedItems){
        this.sharedMovies.remove(sharedItems);
    }
}
