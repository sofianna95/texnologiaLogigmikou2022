//package project;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.Spy;
//import org.mockito.junit.jupiter.MockitoExtension;
//import project.errorhandling.exception.AuthenticationFailedException;
//import project.service.authentication.AuthenticationServiceImpl;
//import project.service.encryption.EncryptionService;
//import project.service.token.TokenService;
//import project.service.user.UserService;
//
//@ExtendWith(MockitoExtension.class)
//class AuthenticationServiceTest {
//
//    @Mock
//    private TokenService tokenService;
//
//    @Spy
//    private EncryptionService encryptionService;
//
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private AuthenticationServiceImpl testee;
//
//    private static final String USERNAME = "username";
//
//    private static final String PASSSWORD = "password";
//
//    @Test
//    void passwordIsValid_validPassword_returnsTrue() {
//        Mockito.when(userService.findByUserName(USERNAME)).thenReturn(getUser());
//
//        Assertions.assertThrows(AuthenticationFailedException.class, () -> testee.authenticateUser(USERNAME, PASSSWORD));
//    }
//
//    private UserDTO getUser() {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setActive(false);
//        userDTO.setUserName("username");
//        userDTO.setPassword("password");
//        return userDTO;
//    }
//}
