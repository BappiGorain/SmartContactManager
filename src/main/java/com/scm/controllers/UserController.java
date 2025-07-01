package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController
{
    // User dashboard page

    @GetMapping("/dashboard")
    public String userDashboard()
    {
        System.out.println("User dashboard page");
        return "user/dashboard";
    }

    // user profile page

     @GetMapping("/profile")
    public String userProfile()
    {
        System.out.println("User profile page");
        return "user/profile";
    }
    
    // User add contacts page

    // User view contacts

    // user edit contact

    // uder delete contact

}
