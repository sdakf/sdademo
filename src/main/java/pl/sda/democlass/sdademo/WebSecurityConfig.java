package pl.sda.democlass.sdademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/todos/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                .antMatchers("/login").permitAll()
                .antMatchers("/console/**").permitAll()
                .antMatchers("/h2/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable().and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login-process")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/").and()
                .logout().logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery("SELECT u.username, u.password_hash,1 FROM user u WHERE u.username=?")
                .authoritiesByUsernameQuery("SELECT u.username, r.role_name, 1 " +
                        "FROM user u " +
                        "INNER JOIN user_role ur ON ur.user_id = u.id " +
                        "INNER JOIN role r ON r.id = ur.roles_id " +
                        "WHERE u.username=?")
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder)
        ;
    }
}
