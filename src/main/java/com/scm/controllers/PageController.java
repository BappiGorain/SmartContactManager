package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController 
{
    @RequestMapping("/home")
    public String home()
    {
        System.out.println("Home page handler");
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage(Model model)
    {
        model.addAttribute("isLogin",true);
        System.out.println("About page loading");
        return "about";
    }

    @RequestMapping("/services")
    public String servicePage()
    {
        System.out.println("Service page loading");
        return "services";
    }

    
    @GetMapping("/contact")
    public String contactPage()
    {
        System.out.println("contact page loading");
        return "contact";
    }

    @GetMapping("/login")
    public String loginPage()
    {
        System.out.println("login page loading");
        return "login";
    }

    @GetMapping("/register")
    public String registerPage()
    {
        System.out.println("register page loading");
        return "register";
    }

    

}
