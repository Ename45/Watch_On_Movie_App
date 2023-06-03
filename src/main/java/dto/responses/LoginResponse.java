package dto.responses;

import data.models.Role;
import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {
    private String message;
    private String fullName;
    private String email;
    private List<String> movieId;
    private Role role;
}
