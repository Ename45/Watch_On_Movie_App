package dto.responses;

import lombok.Data;

@Data
public class SignUpResponse {
    private String message;

    @Override
    public String toString() {
        return "SignUpResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
