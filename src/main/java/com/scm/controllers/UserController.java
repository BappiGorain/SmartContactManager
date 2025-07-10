package com.scm.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.User;
import com.scm.helper.Helper;
import com.scm.services.UserService;


@Controller
@RequestMapping("/user")
public class UserController
{

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    
    
    
    // User dashboard page
    @GetMapping("/dashboard")
    public String userDashboard()
    {
        System.out.println("User dashboard page");
        return "user/dashboard";
    }

    // user profile page

     @GetMapping("/profile")
    public String userProfile(Model model ,Authentication authentication)
    {

        
        System.out.println("User profile page");
        return "user/profile";
    }

    
    
    // User add contacts page

    // User view contacts

    // user edit contact

    // uder delete contact

}
