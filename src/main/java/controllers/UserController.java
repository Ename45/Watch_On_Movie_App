package controllers;

import dto.requests.SignUpRequest;
import dto.responses.SignUpResponse;
import services.UserServices;

public class UserController {
    UserServices userServices;
    public SignUpResponse signUp (SignUpRequest signUpRequest){
        return userServices.signUp(signUpRequest);
    }
}
