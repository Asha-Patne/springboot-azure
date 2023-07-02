package com.java.springboot.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      //  http.csrf().ignoringAntMatchers("/v3/apidoc-openapi/**", "/swagger-ui/**");
   //http.csrf().ignoringAntMatchers("/v3/apidoc-openapi/**", "/swagger-ui/**", "/publishMessage/**","/deleteAllUser/**");
     //   http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    //    http.headers().frameOptions().disable(); // Add this line to allow access to H2 console if you're using it
        http.authorizeRequests()
                .antMatchers("/v3/apidoc-openapi/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("password"))
                .authorities("ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}