package main.security;

import main.security.jwt.JwtSecurityConfigurer;
import main.security.jwt.JwtTokenProvider;
import org.hibernate.metamodel.model.domain.ManagedDomainType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/departments/add").hasRole("ADMIN")
                .antMatchers("/employees/add").hasRole("ADMIN")
                .antMatchers("/depEmps/add").hasRole("ADMIN")
                .antMatchers("/projects/add").hasRole("ADMIN")
                .anyRequest().authenticated();
        http
                .formLogin()
                .loginPage("/login")
                .permitAll();
    }
}
