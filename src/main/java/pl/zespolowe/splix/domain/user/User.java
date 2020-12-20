package pl.zespolowe.splix.domain.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class User implements UserDetails {

    @Id
    @NotBlank(message = "username%Username cannot be blank")
    private String username;

    @NotBlank(message = "password%Password cannot be empty")
    @Size(min = 5, message = "password%Password must be length >= 5")
    private String password;

    private long score;

    @Basic
    private Date lastLogged;

    @Email(message = "email%Invalid email address")
    @NotBlank(message = "email%Email cannot be blank")
    private String email;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private UserSettings settings;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "username", nullable = false))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


    public User() {
        roles = new HashSet<>();
        score = 0;
        settings = new UserSettings(this);
        addRole(Role.USER);
    }

    public User(String username, String password, String email) {
        this();
        this.username = username;
        this.password = password;
        this.email = email;
    }


    public void addRole(Role role) {
        roles.add(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        long diff = Calendar.getInstance().getTime().getTime() - lastLogged.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) <= 90;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonExpired();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isAccountNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return isAccountNonExpired();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.toString())).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
