package studysecurity.multichainproxy.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import studysecurity.multichainproxy.core.domain.student.StudentManager;
import studysecurity.multichainproxy.core.domain.teacher.TeacherManager;

@Order(2)
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final StudentManager studentManager;
    private final TeacherManager teacherManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(studentManager);
        auth.authenticationProvider(teacherManager);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomLoginFilter filter = new CustomLoginFilter(authenticationManager());
        http
                .authorizeRequests()
                .antMatchers("/", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/", false)
                .failureUrl("/login-error")
                .and()
                .addFilterAt(filter, UsernamePasswordAuthenticationFilter.class)
                .logout().logoutSuccessUrl("/")
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
