package org.dadeco.cu996.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;


@EnableWebSecurity
@Configuration("securityConfig")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable().and().authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/", "/overview/**", "/profile","/activity","/plan").authenticated()
                .antMatchers("/users").hasRole("ADMIN")
                .and().formLogin().loginPage("/login")
                .failureUrl("/login?error").permitAll()
                .and().logout().permitAll().and().exceptionHandling().accessDeniedPage("/403");//.and().requiresChannel().anyRequest().requiresSecure();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService());
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}