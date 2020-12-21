package pl.zespolowe.splix.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.zespolowe.splix.services.UserService;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Controller handling basic endpoints like index or error
 *
 * @author Tomasz
 */
@Controller
@Slf4j
public class MainController implements ErrorController {

    @Autowired
    private UserService service;


    @GetMapping("/recover")
    public ResponseEntity<String> recoverPassword(@RequestParam("username") String username) {
        try {
            service.recoverPassword(username);
            return ResponseEntity.ok("Check your email");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/recover/**")
    public String getRecoverPage() {
        return "recovery";
    }

    @PostMapping("/recover/{tokenID}")
    public ResponseEntity<String> changeLostPassword(@PathVariable("tokenID") String token, @RequestBody String password) {
        try {
            service.changeRecoveredPassword(token, password);
            return ResponseEntity.ok("Password changed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * @return Main application page
     */
    @GetMapping("/")
    public String getMainPage() {
        return "index";
    }

    /**
     * @return users with highest score
     */
    @GetMapping(value = "/game/leaders", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Long> getLeaders() {
        return service.getLeaders();
    }


    //ERRORS

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @GetMapping("/error")
    public ResponseEntity<String> err() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}
