package dto.requests;

import data.models.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewMovieDetailsRequest {
    private String movieName;
    private String genre;
    private String userId;
//    private Role role;
    private LocalDateTime year;
    private String producer;
}
