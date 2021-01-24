package pl.zespolowe.splix.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import pl.zespolowe.splix.domain.user.RecoveryToken;
import pl.zespolowe.splix.services.MailService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
public class MailServiceTest {
    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private MailService mailService;


    @Test(expected = Exception.class)
    public void sendTest() {
        MailService mailService = mock(MailService.class);
        doThrow().when(mailService).sendPasswordRecovery(any(String.class), any(RecoveryToken.class));
        mailService.sendPasswordRecovery("mail@mail.com", new RecoveryToken());
    }
}
