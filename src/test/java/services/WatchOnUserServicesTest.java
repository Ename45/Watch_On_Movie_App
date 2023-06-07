package services;

import com.sun.jdi.request.DuplicateRequestException;
//import data.models.Role;
import data.models.Movie;
import data.models.Role;
import data.models.User;
import data.repositories.*;
import dto.requests.LoginRequest;
import dto.requests.NewMovieDetailsRequest;
import dto.requests.SignUpRequest;
import dto.responses.LoginResponse;
import dto.responses.MovieAddedToDatabaseResponse;
import dto.responses.MovieAddedToUserListResponse;
import dto.responses.SignUpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WatchOnUserServicesTest {
    User user;
    UserRepository userRepository;
    UserServices userServices;
    AdminServices adminServices;
    SignUpRequest signUpRequest;
    SignUpResponse signUpResponse;
    LoginRequest loginRequest;
    LoginResponse loginResponse;

    MovieRepository movieRepository;
    Movie movie;
    NewMovieDetailsRequest newMovieDetailsRequest;
    String userMessage;
    String adminMessage;

    @BeforeEach
    void setUp() {
        user = new User();
        userRepository = new WatchOnUserRepository();
        userServices = WatchOnUserServices.getInstance();
        adminServices = new WatchOnAdminServices();
        signUpRequest = new SignUpRequest();
        signUpResponse = new SignUpResponse();
        loginRequest = new LoginRequest();
        loginResponse = new LoginResponse();
        movie = new Movie();
        movieRepository = new WatchOnMovieRepository();
        newMovieDetailsRequest = new NewMovieDetailsRequest();


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

        userMessage = userServices.signUp(signUpRequest).getMessage();

        SignUpRequest signUpRequest2 = new SignUpRequest();
        signUpRequest2.setFullName("Eddy Udousoro");
        signUpRequest2.setEmail("eddy@gmail.com");
        signUpRequest2.setPassword("wOc1472xxX");
        adminMessage = userServices.signUp(signUpRequest2).getMessage();
        String expectedMessage = "Sign Up successful. Check your email for a link to login";
        assertEquals(expectedMessage, userMessage);
        assertEquals(expectedMessage, adminMessage);
    }


    @Test
    @DisplayName("A user is assigned the correct role based on the password pattern")
    public void testSignUpAssignsRoleBasedOnPasswordPattern() {
        signUpRequest.setFullName("Inemesit Udousoro");
        signUpRequest.setEmail("ename@gmail.com");
        signUpRequest.setPassword("I2emf.");

        userMessage = userServices.signUp(signUpRequest).getMessage();

        SignUpRequest signUpRequest2 = new SignUpRequest();
        signUpRequest2.setFullName("Eddy Udousoro");
        signUpRequest2.setEmail("eddy@gmail.com");
        signUpRequest2.setPassword("wOc1472xxX");
        adminMessage = userServices.signUp(signUpRequest2).getMessage();

        List<User> users = userRepository.findAll();
        System.out.println(users);
        assertEquals(Role.USER, users.get(0).getRole());
        assertEquals(Role.ADMIN, users.get(1).getRole());

        assertEquals(1, userRepository.countByRole(Role.USER));
        assertEquals(1, userRepository.countByRole(Role.ADMIN));
    }

    @Test
    @DisplayName("different users can signUp")
    public void twoUserCanSignUpTest(){
//        signUpRequest.setFullName("Inemesit Udousoro");
//        signUpRequest.setEmail("ename@gmail.com");
//        signUpRequest.setPassword("I2em.");
//        userServices.signUp(signUpRequest);

        SignUpRequest signUpRequest3 = new SignUpRequest();
        signUpRequest3.setFullName("Unwana Udousoro");
        signUpRequest3.setEmail("wanz@gmail.com");
        signUpRequest3.setPassword("U3aw.");
        userServices.signUp(signUpRequest3);

//        SignUpRequest signUpRequest3 = new SignUpRequest();
//        signUpRequest3.setFullName("Legends Browse");
//        signUpRequest3.setEmail("leg@gmail.com");
//        signUpRequest3.setPassword("wOc1472xxX");
//        userServices.signUp(signUpRequest3);

        SignUpRequest signUpRequest4 = new SignUpRequest();
        signUpRequest4.setFullName("Oluchi Browse");
        signUpRequest4.setEmail("olu@gmail.com");
        signUpRequest4.setPassword("wOc1482xxX");
        userServices.signUp(signUpRequest4);

        assertEquals(4, userRepository.countUser());

        assertEquals(2, userRepository.countByRole(Role.ADMIN));
        assertEquals(2, userRepository.countByRole(Role.USER));

        List<User> users = userRepository.findAll();
        assertEquals(Role.USER, users.get(0).getRole());
        assertEquals(Role.ADMIN, users.get(1).getRole());
        assertEquals(Role.USER, users.get(2).getRole());
        assertEquals(Role.ADMIN, users.get(3).getRole());
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
    @DisplayName("A user cannot login if data is not in database")
    public void aUserCannotLoginWithoutSigningUpTest(){
        loginRequest.setEmail("ename@gmail.com");
        loginRequest.setPassword("In4m.");

        assertThrows(IllegalArgumentException.class, ()-> userServices.login(loginRequest));
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

        SignUpRequest signUpRequest2 = new SignUpRequest();
        signUpRequest2.setFullName("Legends Browse");
        signUpRequest2.setEmail("leg@gmail.com");
        signUpRequest2.setPassword("wOc1472xxX");
        userServices.signUp(signUpRequest2);
        LoginRequest loginRequest2 = new LoginRequest();
        loginRequest2.setEmail("ename@gmail.com");
        loginRequest2.setPassword("In4m.");


        String expectedMessage = "Logged in.";
        String userMessage = userServices.login(loginRequest).getMessage();
        String user2Message = userServices.login(loginRequest2).getMessage();
        assertEquals(expectedMessage, userMessage);
        assertEquals(expectedMessage, user2Message);
    }

    @Test
    @DisplayName("Two different users can login")
    public void twoUserCanLoginTest(){
        signUpRequest.setFullName("Inemesit Udousoro");
        signUpRequest.setEmail("ename@gmail.com");
        signUpRequest.setPassword("In4m.");
        userServices.signUp(signUpRequest);

        loginRequest.setEmail("ename@gmail.com");
        loginRequest.setPassword("In4m.");
        userServices.login(loginRequest);

        SignUpRequest signUpRequest2 = new SignUpRequest();
        signUpRequest2.setFullName("Legends Browse");
        signUpRequest2.setEmail("leg@gmail.com");
        signUpRequest2.setPassword("L3g8u.");
        userServices.signUp(signUpRequest2);

        loginRequest.setEmail("leg@gmail.com");
        loginRequest.setPassword("L3g8u.");
        userServices.login(loginRequest);

        assertEquals(2, userRepository.countUser());
    }

    @Test
    @DisplayName("A user cannot login with wrong password")
    public void aUserCannotLoginWithAWrongPasswordTest(){
        signUpRequest.setFullName("Inemesit Udousoro");
        signUpRequest.setEmail("ename@gmail.com");
        signUpRequest.setPassword("In4m.");

        userServices.signUp(signUpRequest);

        loginRequest.setEmail("ename@gmail.com");
        loginRequest.setPassword("wOc1472xxX");


        assertThrows(IllegalArgumentException.class, ()-> userServices.login(loginRequest));
    }

    @Test
    void saveMovieToUserList_Successful() {
        NewMovieDetailsRequest newMovieDetailsRequest = new NewMovieDetailsRequest();
        newMovieDetailsRequest.setUserId("2");

        newMovieDetailsRequest.setMovieName("Lord of the rings");
        adminServices.addMovieToDatabase(newMovieDetailsRequest);

        List<Movie> allMovies = adminServices.findAllMovies();
//        System.out.println(allMovies);
//        userServices.findAllMovies().contains()




        signUpRequest.setFullName("Joie");
        signUpRequest.setEmail("joe@gmail.com");
        signUpRequest.setPassword("In3m.");
        userServices.signUp(signUpRequest);

        loginRequest.setEmail("joe@gmail.com");
        loginRequest.setPassword("In3m.");
        loginResponse = userServices.login(loginRequest);

        User currentUser = userServices.findUserById(user.getUserId());

        String movieName = "Lord of the rings";
        MovieAddedToUserListResponse response = userServices.saveMovieToUserList(movieName, currentUser);



        assertNotNull(response);
        assertEquals("Movie added to your list.", response.getMessage());
//        assertTrue(user.getMovieId().contains(foundMovie.getMovieId()));
    }

//    @Test
//    void saveMovieToUserList_InvalidMovieId_ThrowsException() {
//        String movieId = "1";
//        assertThrows(IllegalArgumentException.class, () -> userServices.saveMovieToUserList(movieId));
//    }

}