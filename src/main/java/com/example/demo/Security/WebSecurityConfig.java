package com.example.demo.Security;

import com.example.demo.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    protected BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    protected DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("filter");
        http.authorizeHttpRequests(requests -> requests
                .antMatchers("/").permitAll()
                .antMatchers("webjars/**").permitAll()
                .antMatchers("/books/")
                .hasAnyAuthority("USER","CREATER","EDITOR","ADMIN")
                .antMatchers("/books/add").hasAnyAuthority("ADMIN","CREATER")
                .antMatchers("books/edit/**")
                .hasAnyAuthority("ADMIN","EDITOR")
                .antMatchers("/books/delete/**")
                .hasAnyAuthority("ADMIN").anyRequest()
                .authenticated())
                .formLogin(login -> login.permitAll())
                .logout(logout -> logout.permitAll())
                .exceptionHandling(hangling -> hangling.accessDeniedPage("/403"));
        return http.build();
    }
}
