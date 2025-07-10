package com.scm.helper;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper
{
    public static String getEmailOfLoggedInUser(Authentication authentication)
    {
        
        if(authentication instanceof OAuth2AuthenticationToken)
        {
            var aOAuth2AuthenticationToken= (OAuth2AuthenticationToken)authentication;
            var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2user = (OAuth2User)authentication.getPrincipal();
            String userName = "";
            
            if(clientId.equalsIgnoreCase("google"))
            {
            // cheak wheather the user is signin with google

            System.out.println("User loggedIn using google");
            userName = oauth2user.getAttribute("email").toString();
        
            }
            else if(clientId.equalsIgnoreCase("github"))
            {
            // cheak wheather the user is signin with github

            System.out.println("User loggedIn using github");

            userName = oauth2user.getAttribute("email") != null 
                        ? oauth2user.getAttribute("email").toString() 
                                 : oauth2user.getAttribute("login").toString() + "@github.com";
            
            
            }
            
            return userName;
        }
        else
        {
            System.out.println("User loggedIn using email and password");
            return authentication.getName();
        }
        
    }
}
