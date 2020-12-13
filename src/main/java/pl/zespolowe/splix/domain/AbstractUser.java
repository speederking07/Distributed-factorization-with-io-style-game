package pl.zespolowe.splix.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
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
@MappedSuperclass
public abstract class AbstractUser implements UserDetails {

    @Id
    @NotBlank(message = "username%Username cannot be blank")
    private String username;

    @NotBlank(message = "password%Password cannot be empty")
    @Size(min = 5, message = "password%Password must be length >= 5")
    private String password;

    private long score;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "username", nullable = false))
    @Enumerated(EnumType.STRING)
    private final Set<Role> roles;

    @Basic
    private Date lastLogged;

    public AbstractUser() {
        roles = new HashSet<>();
        score = 0;
    }

    public AbstractUser(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    protected void addRole(Role role) {
        roles.add(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        long diff = Calendar.getInstance().getTime().getTime() - lastLogged.getTime();
        return  TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) <= 90;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isAccountNonExpired();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.toString())).collect(Collectors.toList());
    }
}
