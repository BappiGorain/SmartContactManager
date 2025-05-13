package com.scm.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController
{

    @GetMapping("/dashboard")
    public String userDashboard()
    {
        System.out.println("dashboard");
        return "user/dashboard";
    }

    @GetMapping("profile")
    public String userProfile()
    {
        System.out.println("user profile");
        return "user/profile";
    }


}
