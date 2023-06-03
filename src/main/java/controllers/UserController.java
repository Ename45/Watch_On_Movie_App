package controllers;

import dto.requests.SignUpRequest;
import dto.responses.SignUpResponse;
import org.springframework.web.bind.annotation.RestController;
import services.UserServices;

//@RestController("/")
public class UserController {
    UserServices userServices;
    public SignUpResponse signUp (SignUpRequest signUpRequest){
        return userServices.signUp(signUpRequest);
    }
}
