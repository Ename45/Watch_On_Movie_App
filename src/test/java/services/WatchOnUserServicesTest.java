package services;

import com.sun.jdi.request.DuplicateRequestException;
import data.models.User;
import data.repositories.UserRepository;
import data.repositories.WatchOnUserRepository;
import dto.requests.LoginRequest;
import dto.requests.SignUpRequest;
import dto.responses.LoginResponse;
import dto.responses.SignUpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WatchOnUserServicesTest {
    User user;
    UserRepository userRepository;
    UserServices userServices;
    SignUpRequest signUpRequest;
    SignUpResponse signUpResponse;
    LoginRequest loginRequest;
    LoginResponse loginResponse;

    @BeforeEach
    void setUp() {
        user = new User();
        userRepository = new WatchOnUserRepository();
        userServices = new WatchOnUserServices(userRepository);
        signUpRequest = new SignUpRequest();
        signUpResponse = new SignUpResponse();
        loginRequest = new LoginRequest();
        loginResponse = new LoginResponse();
    }

    @Test
    void userNotNull(){
        assertNotNull(signUpResponse);
    }

    @Test
    @DisplayName("A user can signUp")
    public void aUserCanSignUpTest(){
        signUpRequest.setFullName("Inemesit Udousoro");
        signUpRequest.setEmail("ename@gmail.com");
        signUpRequest.setPassword("I2emf.");

        String expectedMessage = "Sign Up successful. Check your email for a link to login";
        String actualMessage = userServices.signUp(signUpRequest).getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Two different users can signUp")
    public void twoUserCanSignUpTest(){
        signUpRequest.setFullName("Inemesit Udousoro");
        signUpRequest.setEmail("ename@gmail.com");
        signUpRequest.setPassword("I2em.");
        userServices.signUp(signUpRequest);

        SignUpRequest signUpRequest2 = new SignUpRequest();
        signUpRequest2.setFullName("Legends Browse");
        signUpRequest2.setEmail("leg@gmail.com");
        signUpRequest2.setPassword("L3g8u.");
        userServices.signUp(signUpRequest2);

        assertEquals(2, userRepository.countUser());
    }

    @Test
    @DisplayName("A user cannot signUp twice")
    public void userCantSignUpMoreThanOnceTest(){
        signUpRequest.setFullName("Inemesit Udousoro");
        signUpRequest.setEmail("ename@gmail.com");
        signUpRequest.setPassword("I2em.");
        userServices.signUp(signUpRequest);

        SignUpRequest signUpRequest2 = new SignUpRequest();
        signUpRequest2.setFullName("Inemesit Udousoro");
        signUpRequest2.setEmail("ename@gmail.com");
        signUpRequest2.setPassword("I2em.");

        assertEquals(1, userRepository.countUser());
        assertThrows(DuplicateRequestException.class, ()->  userServices.signUp(signUpRequest2));
    }

    @Test
    @DisplayName("A user password should be 5 and above characters long")
    public void userCantSignUpWithLessThan_5_CharPasswordTest(){
        signUpRequest.setFullName("Inemesit Udousoro");
        signUpRequest.setEmail("ename@gmail.com");
        signUpRequest.setPassword("I2em");
        assertThrows(IllegalArgumentException.class, ()->  userServices.signUp(signUpRequest));

        signUpRequest.setPassword("I2em.");
        userServices.signUp(signUpRequest);
        assertEquals(1, userRepository.countUser());
    }

    @Test
    @DisplayName("Password should have at least 1(capital letter, small letter, number, and a special character)")
    public void userPasswordShouldHave_A_MixOfSpecifiedCharTest(){
        signUpRequest.setFullName("Inemesit Udousoro");
        signUpRequest.setEmail("ename@gmail.com");
        signUpRequest.setPassword("In4m.");

        userServices.signUp(signUpRequest);
        assertEquals(1, userRepository.countUser());
    }

    @Test
    @DisplayName("Check That Email is valid")
    public void userEmailIsValidTest(){
        signUpRequest.setFullName("Inemesit Udousoro");
        signUpRequest.setEmail("@gmail.com");
        signUpRequest.setPassword("In4m.");
        assertThrows(IllegalArgumentException.class, ()->  userServices.signUp(signUpRequest), "Invalid email address");

        signUpRequest.setEmail("ename@gmail.com");
        userServices.signUp(signUpRequest);


//        assertNotNull(userRepository.findById(user.getUserId()));
//        assertNotNull(userRepository.findByEmail("enamesit@gmail.com"));
        assertEquals(1, userRepository.findAll().size());
        assertEquals(1, userRepository.countUser());
    }

    @Test
    @DisplayName("A user can login")
    public void aUserCanLoginTest(){
        signUpRequest.setFullName("Inemesit Udousoro");
        signUpRequest.setEmail("ename@gmail.com");
        signUpRequest.setPassword("In4m.");

        userServices.signUp(signUpRequest);

        loginRequest.setEmail("ename@gmail.com");
        loginRequest.setPassword("In4m.");


        String expectedMessage = "Logged in.";
        String actualMessage = userServices.login(loginRequest).getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

}