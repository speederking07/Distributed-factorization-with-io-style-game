package pl.zespolowe.splix.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zespolowe.splix.domain.user.RecoveryToken;
import pl.zespolowe.splix.domain.user.Role;
import pl.zespolowe.splix.domain.user.User;
import pl.zespolowe.splix.repositories.UserRepository;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.CredentialException;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.reverse;

/**
 * @author Tomasz
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private Validator validator;

    private RecoveryToken generateToken(User user) {
        RecoveryToken token = new RecoveryToken();
        token.setToken(UUID.randomUUID().toString());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        token.setValidTo(calendar.getTime());

        token.setUser(user);
        return token;
    }

    public void recoverPassword(String username) throws Exception {
        User user = (User) loadUserByUsername(username);
        RecoveryToken token = generateToken(user);
        user.addToken(token);

        //TODO: usunac po zrobieniu strony do odzyskiwania hasla
        user.setPassword(passwordEncoder.encode(token.getToken()));

        saveUser(user);
        mailService.sendPasswordRecovery(user.getEmail(), token);
    }

    /**
     * Load user from database
     *
     * @param username
     * @throws UsernameNotFoundException username not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("username%User does not exist"));
    }

    public boolean userExists(String username) {
        return userRepository.findById(username).isPresent();
    }

    /**
     * @return 10 players with highest score
     */
    public Map<String, Long> getLeaders() {
        Collection<User> users = userRepository.getTopUsers();
        Map<String, Long> temp = users.stream().collect(Collectors.toMap(User::getUsername, User::getScore));

        List<Map.Entry<String, Long>> list = new ArrayList<>(temp.entrySet());
        list.sort(Map.Entry.comparingByValue());
        reverse(list);

        Map<String, Long> result = new LinkedHashMap<>();
        for (Map.Entry<String, Long> entry : list)
            result.put(entry.getKey(), entry.getValue());

        return result;
    }

    /**
     * Changes user password
     *
     * @param user
     * @param password
     * @throws Exception password not valid
     */
    public void changePassword(@NonNull User user, String password) throws Exception {
        String temp = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (violations.isEmpty()) saveUser(user);
        else {
            user.setPassword(temp);
            throw new CredentialException(violations.stream().findFirst().get().getMessage());
        }
    }

    /**
     * Changes user email
     *
     * @param user
     * @param email
     * @throws Exception email not valid
     */
    public void changeEmail(@NonNull User user, String email) throws Exception {
        String temp = user.getEmail();
        user.setEmail(email);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (violations.isEmpty()) saveUser(user);
        else {
            user.setEmail(temp);
            throw new CredentialException(violations.stream().findFirst().get().getMessage());
        }
    }

    /**
     * Gives user Administrator privilege
     *
     * @param username
     * @return
     * @throws AccountException user does not exist
     */
    public User setAdmin(String username) throws AccountException {
        if (!userExists(username)) throw new AccountException("User does not exist");
        User user = (User) loadUserByUsername(username);
        user.addRole(Role.ADMIN);
        saveUser(user);
        return user;
    }

    /**
     * @param user
     * @throws AccountException username already exists
     */
    public void registerUser(@NonNull User user) throws AccountException {
        if (userExists(user.getUsername())) throw new AccountException("User already exist");
        user.setLastLogged(new Date(Calendar.getInstance().getTime().getTime()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        saveUser(user);
    }

    /**
     * Save user to database
     *
     * @param user
     */
    public void saveUser(@NonNull User user) {
        userRepository.save(user);
    }
}
