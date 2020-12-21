package pl.zespolowe.splix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.zespolowe.splix.domain.user.RecoveryToken;

/**
 * @author Tomasz
 */
@Service
public class MailService {

    @Autowired
    private JavaMailSender emailSender;


    @Async
    public void sendPasswordRecovery(String mail, RecoveryToken token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("zespolowe2020@gmail.com");
        message.setTo(mail);
        message.setSubject("Cool Gama - password recovery");
        message.setText("Wejdz w ten link, aby zresetować hasło:\n http://localhost:8080/recover/" + token.getToken());
        //message.setText("Oto Twoje nowe hasło, możesz je zmienić w ustawieniach po zalogowaniu.\n" + token.getToken());
        emailSender.send(message);
    }
}
