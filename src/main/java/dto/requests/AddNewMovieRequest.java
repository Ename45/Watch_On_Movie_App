package dto.requests;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AddNewMovieRequest {
    private String movieName;
    private String genre;
    private LocalDateTime year;
    private String producer;
}
