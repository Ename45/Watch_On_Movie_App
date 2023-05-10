package dto.requests;

import lombok.Data;

@Data
public class SignUpRequest {
    private String fullName;
    private String email;
    private String password;
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
}
