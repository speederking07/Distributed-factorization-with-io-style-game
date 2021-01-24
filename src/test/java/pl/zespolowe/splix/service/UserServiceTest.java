package pl.zespolowe.splix.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.zespolowe.splix.domain.user.User;
import pl.zespolowe.splix.repositories.UserRepository;
import pl.zespolowe.splix.services.MailService;
import pl.zespolowe.splix.services.UserService;

import javax.security.auth.login.AccountException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private MailService mailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;


    @Before
    public void init() {
        when(userRepository.findById("USERNAME")).thenReturn(Optional.of(new User("USERNAME", "pass", "mail")));
        when(userRepository.findById("username")).thenReturn(Optional.empty());
    }

    @Test
    public void userExistsTest() {
        assertTrue(userService.userExists("USERNAME"));
        assertFalse(userService.userExists("username"));
    }

    @Test
    public void loadUserTest() {
        UserDetails userDetails = userService.loadUserByUsername("USERNAME");
        assertSame("pass", userDetails.getPassword());
    }

    @Test
    public void existingUserRegisterTest() {
        User u = new User("USERNAME", "password", "email@gmail.com");
        assertThrows(AccountException.class, () -> userService.registerUser(u));
    }

    @Test
    public void userRegisterTest() {
        User proper = new User("username", "password", "email@gmail.com");
        assertDoesNotThrow(() -> userService.registerUser(proper));
    }

}
