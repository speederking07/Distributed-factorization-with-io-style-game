package pl.zespolowe.splix.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import pl.zespolowe.splix.config.security.AuthProvider;
import pl.zespolowe.splix.domain.User;
import pl.zespolowe.splix.services.UserService;

import java.sql.Date;
import java.util.Calendar;


@Configuration
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    private UserService userService;

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return (req, res, e) -> {
            res.setStatus(400);
            res.setContentType("text/plain");
            res.getWriter().write(e.getMessage());
            //res.getWriter().flush();
            res.getWriter().close();
        };
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (req, res, authentication) -> {
            if (authentication.getPrincipal() instanceof User) {
                User user = (User) authentication.getPrincipal();
                user.setLastLogged(new Date(Calendar.getInstance().getTime().getTime()));
                userService.saveUser(user);
            }
        };
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver vr = new InternalResourceViewResolver();
        vr.setPrefix("/WEB-INF/jsp/");
        vr.setSuffix(".jsp");
        return vr;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**",
                "/css/**",
                "/img/**",
                "/js/**",
                "/font/**")
                .addResourceLocations("classpath:/static/",
                        "classpath:/static/css/",
                        "classpath:/static/img/",
                        "classpath:/static/js/",
                        "classpath:/static/font/");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthProvider authenticationProvider() {
        return new AuthProvider();
    }
}
