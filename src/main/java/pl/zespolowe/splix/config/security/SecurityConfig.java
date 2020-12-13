package pl.zespolowe.splix.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthProvider authenticationProvider;


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        assert auth != null;
        auth.authenticationProvider(authenticationProvider);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.addFilterBefore(createCustomFilter(), AnonymousAuthenticationFilter.class)
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/resources/**", "/leaders", "/play", "/js/**", "/css/**", "/font/**", "/img/**", "/game/**").permitAll() // "/stomp/**", "/gameStompEndpoint", "/topic/**"
                .antMatchers("/register", "/login").anonymous()
                .antMatchers("/logout", "/account/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/login")
                //.defaultSuccessUrl("/", true)
                .failureHandler(failureHandler)
                .successHandler(successHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
                .rememberMe().tokenValiditySeconds(84600 * 30)
                .and()
                .exceptionHandling().accessDeniedPage("/");
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .maximumSessions(1);
    }

}
