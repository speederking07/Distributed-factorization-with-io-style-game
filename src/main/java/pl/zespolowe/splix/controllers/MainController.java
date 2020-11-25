package pl.zespolowe.splix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.zespolowe.splix.domain.Role;
import pl.zespolowe.splix.domain.User;
import pl.zespolowe.splix.services.UserService;

import javax.security.auth.login.AccountException;
import javax.validation.Valid;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
public class MainController {

    @Autowired
    private UserService service;


    @GetMapping("/")
    public String getMainPage() {
        return "index";
    }

    @PostMapping(value = "/register", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        try {
            service.registerUser(user);
            return ResponseEntity.ok("Registration successful");
        } catch (AccountException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    //TODO
    @GetMapping("/admin")
    public String getAdminConsole() {
        return "admin";
    }

    //TODO
    @GetMapping(value = "/admin/register", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody User user) {
        try {
            user.addRole(Role.ADMIN);
            service.registerUser(user);
            return ResponseEntity.ok("Registration successful");
        } catch (AccountException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping(value = "/leaders", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Long> getLeaders(){
        return service.getLeaders();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String message =  e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

}
