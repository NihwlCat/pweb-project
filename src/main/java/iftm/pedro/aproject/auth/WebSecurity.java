package iftm.pedro.aproject.auth;

import iftm.pedro.aproject.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService service;

    public WebSecurity(UserService service) {
        this.service = service;
    }

    @Bean
    BCryptPasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder(4);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(getEncoder());
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        security.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET,"/").permitAll()
                .antMatchers("/h2/**").permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers("/css/**","/images/**","/js/**").permitAll()
                .antMatchers("/panel/items/**").hasAuthority("ADMIN")
                .antMatchers("/panel/payloads/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                    .addFilter(new TokenAuthFilter(service, authenticationManager()))
                    .addFilter(new TokenVerifyFilter(authenticationManager()))
                .formLogin().loginPage("/login").permitAll();

        security.headers().frameOptions().disable();
    }
}
