package data.models;

import lombok.Data;

import java.time.LocalDateTime;
@Data

public class Movie {
    private String movieId;
    private String movieName;
    private String genre;
    private LocalDateTime year;
    private String producer;
}
