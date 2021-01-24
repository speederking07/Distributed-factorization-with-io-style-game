package pl.zespolowe.splix.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.zespolowe.splix.domain.user.Role;
import pl.zespolowe.splix.domain.user.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    private List<User> users = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;


    @Before
    public void init() {
        users = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            User u = new User("username" + i, "password", "mail@mail.com");
            u.setScore(i + 1000000);
            users.add(u);
        }
        userRepository.saveAll(users);
    }

    @After
    public void remove() {
        userRepository.deleteAll(users);
    }

    @Test
    public void findByUsername() {
        Optional<User> found = userRepository.findById("username15");
        assertTrue(found.isPresent());
        User user = found.get();
        assertEquals(user.getUsername(), "username15");
        assertEquals(user.getScore(), 15 + 1000000);
    }

    @Test
    public void topPlayersTest() {
        Collection<User> top = userRepository.getTopUsers();
        assertEquals(top.size(), 10);
        int i = 1000000 + 19;
        for (User u : top)
            assertEquals(u.getScore(), i--);
    }

    @Test
    public void defaultRoleTest() {
        for (User u : users) {
            Optional<User> temp = userRepository.findById(u.getUsername());
            assertTrue(temp.isPresent());
            assertEquals(temp.get().getRoles().size(), 1);
            assertTrue(temp.get().getRoles().contains(Role.USER));
        }
    }

    @Test
    public void updateTest() {
        User u = users.get(0);
        u.addRole(Role.ADMIN);
        userRepository.save(u);

        Optional<User> opt = userRepository.findById(u.getUsername());
        assertTrue(opt.isPresent());
        assertEquals(opt.get().getRoles().size(), 2);
        assertTrue(opt.get().getRoles().contains(Role.USER));
        assertTrue(opt.get().getRoles().contains(Role.ADMIN));
    }
}
