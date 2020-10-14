package pl.zespolowe.splix.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthProvider authenticationProvider;


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        assert auth != null;
        auth.authenticationProvider(authenticationProvider);
    }


    //TODO
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    //replacing Anonymous filter to give every anonymous user unique name
                //.addFilterAt(new CustomAnonymousFilter(), AnonymousAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest()
                .permitAll();
    }


}
