package pl.zespolowe.splix.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.zespolowe.splix.domain.user.User;
import pl.zespolowe.splix.services.UserService;

import javax.security.auth.login.AccountException;
import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Controller
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping(value = "/register", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        try {
            service.registerUser(user);
            log.info("Registered: " + user);
            return ResponseEntity.ok("Registration successful");
        } catch (AccountException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //TODO
    @GetMapping("/admin")
    public String getAdminConsole() {
        return "admin";
    }


    //TODO
    @GetMapping(value = "/admin/set", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody String username) {
        try {
            User user = service.setAdmin(username);
            log.info("Set admin: " + user);
            return ResponseEntity.ok("Success");
        } catch (AccountException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
