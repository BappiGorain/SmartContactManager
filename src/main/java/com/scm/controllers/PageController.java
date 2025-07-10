package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController 
{

    @Autowired
    private UserService userService;

    @RequestMapping("/home")
    public String home()
    {
        System.out.println("Home page handler");
        return "home";
    }

    @RequestMapping("/")
    public String index()
    {
        return "redirect:/home";
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
    public String processingRegister(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult ,HttpSession session)
    {
        System.out.println("Processing registation");
        // Fetch the data
        // UserForm
        System.out.println(userForm);
        // validate form data

        if(rBindingResult.hasErrors())
        {
            return "register";
        }


        // save to database
        // User Service'
        
        // UserForm --> User
        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic("https://img.freepik.com/free-vector/blue-circle-with-white-user_78370-4707.jpg?semt=ais_items_boosted&w=740")     
        // .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setAbout(userForm.getAbout());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("https://img.freepik.com/free-vector/blue-circle-with-white-user_78370-4707.jpg?semt=ais_items_boosted&w=740");

        User savedUser = userService.saveUser(user);

        System.out.println("Saved user" + savedUser);

        // message = "registration successful"

        Message message = Message.builder().content("Registration successful").type(MessageType.green).build();

        session.setAttribute("message",message);


        // redirect to login form

        return "redirect:/register";
    }
}
