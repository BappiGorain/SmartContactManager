package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.services.impl.SecurityCustomUserDetailService;


@Configuration
public class SecurityConfig
{

    
    // User Create and login using inMemory Service
    // @Bean
    // public UserDetailsService detailsService()
    // {
    //     UserDetails user1 = User
    //     .withDefaultPasswordEncoder()
    //     .username("admin123")
    //     .password("admin123")
    //     .build();

    //     UserDetails user2 = User
    //     .withDefaultPasswordEncoder()
    //     .username("user123")
    //     .password("user123")
    //     .build();



    //     var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1,user2);
    //     return inMemoryUserDetailsManager;
    // }

    @Autowired
    private SecurityCustomUserDetailService userDetailService;

    @Autowired 
    private OAuthAuthenticationSuccessHandler handler;


    @Bean
    public DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        // User detail service ka object
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        // password encoder ka object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    


    // public and private urls configured here
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        // Configuration 

        httpSecurity.authorizeHttpRequests(authorize->{
            authorize.requestMatchers("/home","/register","/services","/do-register").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();

        });

 
        httpSecurity.formLogin(formLogin->{            
            
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.defaultSuccessUrl("/user/dashboard", true);
            // formLogin.successForwardUrl("/user/dashboard");
            // formLogin.failureForwardUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
            
        });


        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutForm->{
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        // oauth configurations

        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });

        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }


}
