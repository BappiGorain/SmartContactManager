package com.scm.config;

import com.scm.services.impl.SecurityCustomUserDetailService;
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

@Configuration
public class SecurityConfig
{
//    @Bean
//    public UserDetailsService userDetailsService()
//    {
//
//        UserDetails user1 = User
//                .withDefaultPasswordEncoder()
//                .username("user123")
//                .password("user123")
//                .build();
//
//        UserDetails user2 = User
//                .withDefaultPasswordEncoder()
//                .username("admin123")
//                .password("admin123")
//                .build();
//
//
//        return new InMemoryUserDetailsManager(user1,user2);
//    }

    @Autowired
    private SecurityCustomUserDetailService userDetailService;



    @Bean
    public DaoAuthenticationProvider authenticationProvider()
    {
         DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
         daoAuthenticationProvider.setUserDetailsService(userDetailService);
         daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {

        // Configuration
        // url configure
        httpSecurity.authorizeHttpRequests(authorize->{
//            authorize.requestMatchers("/home","/register","/services").permitAll();  // public urls
            authorize.requestMatchers("/user/**").authenticated();  // private urls
            authorize.anyRequest().permitAll();
        });


        // Default Form default login
//        httpSecurity.formLogin(Customizer.withDefaults());

        httpSecurity.formLogin(formLogin->{
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticated");
            formLogin.successForwardUrl("/user/dashboard");
            formLogin.failureForwardUrl("/login?error=true");

            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutForm->{
           logoutForm.logoutUrl("/logout");
           logoutForm.logoutSuccessUrl("/do-logout?logout=true");
        });


        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return  new BCryptPasswordEncoder();
    }


}
