package com.scm.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper
{
    public static void removeMessage()
    {
        System.out.println("Removing message from session helper");
        try {
            HttpSession session =  ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.removeAttribute("message");
            
        } catch (Exception e) {
            System.out.println("Error in message Helper");
            e.printStackTrace();
        }
    }
        
}
