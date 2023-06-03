package dto.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewMovieDetailsRequest {
    private String movieName;
    private String genre;
    private String userId;
    private LocalDateTime year;
    private String producer;
}
