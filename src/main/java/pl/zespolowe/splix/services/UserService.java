package pl.zespolowe.splix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zespolowe.splix.domain.User;
import pl.zespolowe.splix.repositories.UserRepository;

import javax.security.auth.login.AccountException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.reverse;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
    }

    public boolean userExists(String username) {
        return userRepository.findById(username).isPresent();
    }

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

    public void registerUser(User user) throws AccountException {
        if (userExists(user.getUsername())) throw new AccountException("User already exist");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
