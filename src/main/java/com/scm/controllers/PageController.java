package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.forms.UserForm;

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
    public String registerPage(Model model)
    {
        UserForm userForm = new UserForm();
        // Default value set
        // userForm.setName("Bappi");
        // userForm.setEmail("abc@gmail.com");
        // userForm.setPassword("12345");
        // userForm.setPhoneNumber("1234567890");
        // userForm.setAbout("this user is only for testing");
        model.addAttribute("userForm", userForm);

        System.out.println("register page loading");
        return "register";
    }

    // Processing register

    @PostMapping("/do-register")
    public String processingRegister(@ModelAttribute UserForm userForm)
    {
        System.out.println("Processing registation");
        // Fetch the data
        // UserForm
        System.out.println(userForm);
        // validate form data
        // save to database
        // User Service

        // message = "registration successful"
        // redirect to login form

        return "redirect:/register";
    }
}
