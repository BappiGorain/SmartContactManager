package com.scm.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helper.Helper;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.ContactService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;


    @GetMapping("/add")   
    public String addContactView(Model model)
    {
        ContactForm contactForm = new ContactForm();

        // Default data
        model.addAttribute("contactForm", contactForm);
        // contactForm.setName("Bappi");
        // contactForm.setEmail("bappigorain@gmail.com");
        // contactForm.setPhoneNumber("1234567890");
        // contactForm.setAddress("This is my address");
        // contactForm.setDescription("This is for testing purpose");
        // contactForm.setFavorite(true);

        System.out.println("Contact page");
        return "user/add_contact";
    }



    @PostMapping("/add")
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm,BindingResult result,Authentication authentication, HttpSession session)
    {


        if(result.hasErrors())
        {
            session.setAttribute("message", Message.builder()
            .content("Please correct the following errors")
            .type(MessageType.red)
            .build());
            return "user/add_contact";
        }
        
        
        
        
        String userName = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(userName);
        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setFavorite(contactForm.isFavorite());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setDescription(contactForm.getDescription());
        contact.setAddress(contactForm.getAddress());
        contact.setUser(user);
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contactService.save(contact);
        System.out.println(contactForm);
        session.setAttribute("message",Message.builder()
        .content("You have successfully added a new contact")
        .type(MessageType.green)
        .build());
        return "redirect:/user/contacts/add";
    }
}
