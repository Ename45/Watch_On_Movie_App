package dto.requests;

import data.models.User;
import lombok.Data;

@Data
public class ShareMovieRequest {
    String movieId;
    String senderId;
    String receiverId;
}
