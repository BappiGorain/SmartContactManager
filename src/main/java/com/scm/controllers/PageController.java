package com.scm.controllers;

import com.scm.entities.User;
import com.scm.forms.UserForm;

import com.scm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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


    @RequestMapping("/register")
    public String register(Model model)
    {

        UserForm userForm = new UserForm();

        // Default data
//        userForm.setName("Bappi");
//        userForm.setAbout("This is about you");
        model.addAttribute("userForm",userForm);



        System.out.println("Login page");
        return "register";
    }


//    processing register


    @RequestMapping(value = "/do-register",method=RequestMethod.POST)
    public String processRegister(@ModelAttribute UserForm userForm)
    {
        System.out.println("Processing registration");

        // we have to fetch the data
        System.out.println(userForm);

        // validate the data


        // save in the db

        // userService

//        User user = User.builder()
//                .name(userForm.getName())
//                .email(userForm.getEmail())
//                .password(userForm.getPassword())
//                .about(userForm.getAbout())
//                .phoneNumber(userForm.getPhoneNumber())
//                .profilePic("https://www.google.com/url?sa=i&url=https%3A%2F%2Fuxwing.com%2Fdefault-profile-picture-male-icon%2F&psig=AOvVaw2B3DtIDI0N-u1sV-jZX1oL&ust=1747056546697000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCJDalvvCm40DFQAAAAAdAAAAABAE")
//                .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("https://www.google.com/url?sa=i&url=https%3A%2F%2Fuxwing.com%2Fdefault-profile-picture-male-icon%2F&psig=AOvVaw2B3DtIDI0N-u1sV-jZX1oL&ust=1747056546697000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCJDalvvCm40DFQAAAAAdAAAAABAE");


        userService.saveUser(user);

        System.out.println("user saved");


        // message = "Successful"
        // redirect to login page
        return "redirect:/register";
    }

}
