package data.models;

import lombok.Data;

import java.util.List;
@Data
public class User {
    private String userId;
    private String fullName;
    private String email;
    private String password;
    private List<String> movieId;
    private Role role;
//    private boolean
}
