package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.controllers.repositories.UserRepo;
import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helper.AppConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.var;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException 
    {
            logger.info("OAuthAuthenticationSuccesshandler");


            // Identify the provider

             var  oauth2AuthenticationToken= (OAuth2AuthenticationToken) authentication;
             String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

             logger.info(authorizedClientRegistrationId);


             var oauthUser = (DefaultOAuth2User)authentication.getPrincipal();

            oauthUser.getAttributes().forEach((key,value)->{
                logger.info(key + " : " + value);
            });

            User user = new User();
            user.setUserId(UUID.randomUUID().toString());
            user.setRoleList(List.of(AppConstants.ROLE_USER));
            user.setEmailVerified(true);
            user.setEnable(true);


               if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
                // google
                // google attribute

                user.setEmail(oauthUser.getAttribute("email").toString());
                user.setPassword("dummy");
                user.setProfilePic(oauthUser.getAttribute("picture").toString());
                user.setName(oauthUser.getAttribute("name").toString());
                user.setProviderUserId(oauthUser.getName());
                user.setAbout("This account is created using google");
                user.setProvider(Providers.GOOGLE);
                
             }

              if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
                // github
                // github attribute

              String email = oauthUser.getAttribute("email") != null 
                        ? oauthUser.getAttribute("email").toString() 
                                 : oauthUser.getAttribute("login").toString() + "@github.com";

                
               String picture = oauthUser.getAttribute("avatar_url").toString();
               String name = oauthUser.getAttribute("login").toString();
               String providerUserId = oauthUser.getName();

               user.setEmail(email);
               user.setPassword("dummy");
               user.setProfilePic(picture);
               user.setAbout("This user is created using githhub");
               user.setName(name);
               user.setProviderUserId(providerUserId);
               user.setProvider(Providers.GITHUB);
                
                
             }
             else if (authorizedClientRegistrationId.equalsIgnoreCase("linkedin")) {
                // linkedIn
                // linkedIn attribute
             }
             else if (authorizedClientRegistrationId.equalsIgnoreCase("facebook")) {
                // facebook
                // facebook attribute
             }
             else
             {
                logger.info("OAuthAuthenticationSuccessHandler : unknown provider");
             }

            User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);
            if(user2==null)
            {
               System.out.println("User saved + " + user.getEmail());
               userRepo.save(user); 
            }

            new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");


             

            /*


            DefaultOAuth2User  user = (DefaultOAuth2User)authentication.getPrincipal();
            // logger.info(user.getName());


            // user.getAttributes().forEach((key,value) ->{
            //     logger.info("{} => {}",key,value);
            // });

            // logger.info(user.getAuthorities().toString());

            String email = user.getAttribute("email").toString();
            String name = user.getAttribute("name").toString();
            String picture = user.getAttribute("picture").toString();

            // Create user and save to database

            User user1 = new User();
            user1.setEmail(email);
            user1.setName(name);
            user1.setProfilePic(picture);
            user1.setPassword("password");
            user1.setUserId(UUID.randomUUID().toString());
            user1.setProvider(Providers.GOOGLE);
            user1.setEnable(true);
            user1.setEmailVerified(true);
            user1.setProviderUserId(user.getName());
            user1.setRoleList(List.of(AppConstants.ROLE_USER));
            user1.setAbout("This account is created using google..");

            User user2 = userRepo.findByEmail(email).orElse(null);

            if(user2==null)
            {
                userRepo.save(user1); 
                logger.info("user Saved : " + email);
            }

             
            
            new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

            */


    }

}
