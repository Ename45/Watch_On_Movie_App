package services;

import dto.requests.LoginRequest;
import dto.requests.SignUpRequest;
import dto.responses.LoginResponse;
import dto.responses.SignUpResponse;

public interface UserServices {
    public SignUpResponse signUp (SignUpRequest signUpRequest);
    public LoginResponse login (LoginRequest loginRequest);
}
