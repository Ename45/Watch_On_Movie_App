package dto.responses;

import lombok.Data;

@Data
public class MovieAddedToDatabaseResponse {
    private String movieId;
    private String message;
}
