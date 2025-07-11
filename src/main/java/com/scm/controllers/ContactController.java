package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.forms.ContactForm;


@Controller
@RequestMapping("/user/contacts")
public class ContactController {


    @GetMapping("/add")   
    public String addContactView(Model model)
    {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        contactForm.setName("Bappi");
        contactForm.setEmail("bappigorain@gmail.com");
        contactForm.setPhoneNumber("1234567890");
        contactForm.setAddress("This is my address");
        contactForm.setDescription("This is for testing purpose");
        contactForm.setFavorite(true);

        System.out.println("Contact page");
        return "user/add_contact";
    }



    @PostMapping("/add")
    public String saveContact()
    {
        return "redirect:/user/contacts/add";
    }

}
