package com.scm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
public class SecurityConfig
{

    // User Create and login using inMemory Service
    @Bean
    public UserDetailsService detailsService()
    {


        UserDetails user1 = User
        .withDefaultPasswordEncoder()
        .username("admin123")
        .password("admin123")
        .build();

        UserDetails user2 = User
        .withDefaultPasswordEncoder()
        .username("user123")
        .password("user123")
        .build();



        var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1,user2);
        return inMemoryUserDetailsManager;
    }


}
