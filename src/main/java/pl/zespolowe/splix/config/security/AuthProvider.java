package pl.zespolowe.splix.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.zespolowe.splix.services.UserService;

@Component
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails registeredUserDetails = userService.loadUserByUsername(username);

        if(!registeredUserDetails.isEnabled())
            throw new AccountExpiredException("username%Account expired");

        if (!passwordEncoder.matches(password, registeredUserDetails.getPassword()))
            throw new BadCredentialsException("password%Password incorrect");

        return new UsernamePasswordAuthenticationToken(registeredUserDetails, password, registeredUserDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.equals(aClass) ;
    }
}