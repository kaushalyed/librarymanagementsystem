package com.test.librarymanagementsystem.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
//@Import(Encoders.class)
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
    private UserDetailsService userService;
    
   /* @Autowired
    private PasswordEncoder userPasswordEncoder;*/
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
            http.
               authorizeRequests().antMatchers("/", "/login").permitAll()
                .antMatchers("/home","/test","/users", "/authors", "/books").hasRole("Admin")
                .antMatchers("/home","/test","/books", "/authors").access("hasRole('User')")
                .anyRequest().authenticated().and()
                .csrf().disable()
                 .formLogin().loginPage("/login")
                  .permitAll()
                  .failureUrl("/login?error=true")
                .defaultSuccessUrl("/home")
                .and()
                .logout().permitAll();
                //.logoutRequestMatcher(new AntPathRequestMatcher("/logout.jsp"))
                //.logoutSuccessUrl("/");
    }*/
}
