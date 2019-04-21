package teodorlazarov.getfood.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static teodorlazarov.getfood.constants.GlobalConstants.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf()
//                .disable()
//                .cors()
//                .disable()
                .authorizeRequests()
                .antMatchers("/", "/login", "/register").anonymous()
                .antMatchers("/admin", "/admin/**").hasAnyAuthority(USER_ROLE_EMPLOYEE, USER_ROLE_ROOT, USER_ROLE_ADMIN)
                .antMatchers("/css/**", "/js/**", "/static/img/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/menu")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/menu")
                .and()
                .logout()
                .logoutSuccessUrl("/");
    }
}
