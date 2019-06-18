package com.test.librarymanagementsystem.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
                .resourceId("lms").tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                /*.antMatchers("/","/**").permitAll();*/
                .antMatchers("/", "/login").permitAll()
                .antMatchers("/oauth/token").permitAll()
                //.antMatchers("/home","/users/**","/authors/**","/books/**").hasAuthority("Admin")
                //.antMatchers("/home", "/authors/**").hasAuthority("User")
                .antMatchers("/home","/users/**","/book-transaction/**").access("hasAuthority('Admin')")
                .antMatchers("/home", "/authors/**","/books/**").hasAnyAuthority("Admin","User")
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
    }
}
