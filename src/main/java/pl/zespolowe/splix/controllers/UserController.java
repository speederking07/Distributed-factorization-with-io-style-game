package pl.zespolowe.splix.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.zespolowe.splix.domain.user.User;
import pl.zespolowe.splix.dto.UserSettingsDTO;
import pl.zespolowe.splix.services.UserService;

import javax.security.auth.login.AccountException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

/**
 * Controller handling any actions changing User account data
 *
 * @author Tomasz
 * @see User
 */
@Slf4j
@Controller
public class UserController {

    @Autowired
    private UserService service;

    /**
     * Register new user
     *
     * @param user filled user object
     * @return success/error message
     */
    @PostMapping(value = "/register", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@Valid @RequestBody User user, HttpServletRequest request) {
        try {
            log.info("Registration attempt: " + user.getUsername() + ", " + user.getEmail() + ", " + request.getRemoteAddr());
            service.registerUser(user);
            return ResponseEntity.ok("Registration successful");
        } catch (AccountException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * @param authentication
     * @return user preferences
     * @see pl.zespolowe.splix.domain.user.UserSettings
     */
    @GetMapping(value = "/user/settings", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserSettingsDTO> getSettings(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(new UserSettingsDTO(user.getSettings()));
    }

    /**
     * Update user settings
     *
     * @param dto  User setting dto
     * @param auth
     * @return 200 if settings valid
     */
    @PostMapping(value = "/user/settings", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateSettings(@Valid @RequestBody UserSettingsDTO dto, Authentication auth) {
        if (auth.getPrincipal() instanceof User) {
            User user = (User) auth.getPrincipal();
            user.getSettings().updateFromDto(dto);
            service.saveUser(user);
        }
        return ResponseEntity.ok("");
    }

    /**
     * Update user password
     *
     * @param password       new password
     * @param authentication
     * @return success/error message
     */
    @PostMapping(value = "/user/password", consumes = TEXT_PLAIN_VALUE)
    public ResponseEntity<String> changePassword(@RequestBody String password, Authentication authentication, HttpServletRequest request) {
        if (!(authentication.getPrincipal() instanceof User)) return ResponseEntity.ok("");
        try {
            User user = (User) authentication.getPrincipal();
            log.info("Password change attempt: " + user.getUsername() + ", " + request.getRemoteAddr());
            service.changePassword(user, password);
            return ResponseEntity.ok("password%Password changed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Update user email
     *
     * @param email          new email
     * @param authentication
     * @return success/error message
     */
    @PostMapping(value = "/user/email", consumes = TEXT_PLAIN_VALUE)
    public ResponseEntity<String> changeEmail(@RequestBody String email, Authentication authentication, HttpServletRequest request) {
        if (!(authentication.getPrincipal() instanceof User)) return ResponseEntity.ok("");
        try {
            User user = (User) authentication.getPrincipal();
            log.info("Email change attempt: " + user.getUsername() + ", " + request.getRemoteAddr());
            service.changeEmail(user, email);
            return ResponseEntity.ok("email%Email changed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //TODO
    @GetMapping("/admin")
    public String getAdminConsole() {
        return "admin";
    }


    //TODO
    @GetMapping(value = "/admin/set", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerAdmin(@RequestBody String username, Authentication auth, HttpServletRequest request) {
        try {
            User admin = (User) auth.getPrincipal();
            log.info("Set admin attempt for: " + username + " by: " + admin.getUsername() + ", " + request.getRemoteAddr());
            User user = service.setAdmin(username);
            return ResponseEntity.ok("Success");
        } catch (AccountException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
