package pl.zespolowe.splix.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.zespolowe.splix.domain.user.RecoveryToken;
import pl.zespolowe.splix.domain.user.User;

import javax.validation.Validator;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidationTest {
    @Autowired
    private Validator validator;

    @Test
    public void recoveryTokenValidationDateTest() throws InterruptedException {
        RecoveryToken token = RecoveryToken.generateToken();
        assertTrue(token.isValid());

        Calendar calendar = Calendar.getInstance();
        token.setValidTo(calendar.getTime());
        Thread.sleep(100);
        assertFalse(token.isValid());
    }

    @Test
    public void userValidationTest() {
        User user = new User("username", "password", "mail@mail.com");
        assertTrue(validator.validate(user).isEmpty());

        user.setPassword("shrt");
        assertFalse(validator.validate(user).isEmpty());
        user.setPassword("password");

        user.setUsername("");
        assertFalse(validator.validate(user).isEmpty());
        user.setUsername("username");

        user.setEmail("wrong");
        assertFalse(validator.validate(user).isEmpty());
    }


}
