package com.scm.controllers;


import java.util.UUID;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.helper.AppConstants;
import com.scm.helper.Helper;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMethod;




@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);


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


        logger.info("File information : " + contactForm.getContactImage().getOriginalFilename());

        String fileName = UUID.randomUUID().toString();

        String fileURL=imageService.uploadImage(contactForm.getContactImage(),fileName);
        
        
        
        
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
        contact.setPicture(fileURL);
        contact.setCloudinaryImagePublicId(fileName);
        contactService.save(contact);
        System.out.println(contactForm);

        session.setAttribute("message",Message.builder()
        .content("You have successfully added a new contact")
        .type(MessageType.green)
        .build());
        return "redirect:/user/contacts/add";
    }


    // view Contacts

    @RequestMapping
    public String viewContacts(
        @RequestParam(value = "page",defaultValue = "0") int page,
        @RequestParam(value = "size",defaultValue = AppConstants.PAGE_SIZE+"") int size,
        @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
        @RequestParam(value = "direction",defaultValue = "asc") String direction
        ,Model model,Authentication authentication)
    {
        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);

        Page<Contact> pageContact = contactService.getByUser(user,page,size,sortBy,direction);

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());
        
        System.out.println("contact view page");
        return "user/contacts";
    }    

    // Search handler
    @GetMapping("/search")
    public String searchHandler(@ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "size",defaultValue = AppConstants.PAGE_SIZE+"") int size,
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
            @RequestParam(value = "direction",defaultValue = "asc") String direction,
            Model model,Authentication authentication
            )
    {

        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
        
        
        logger.info("field : {} keyword {}",contactSearchForm.getField(),contactSearchForm.getValue());

        Page<Contact> pageContact = null;

        if(contactSearchForm.getField().equalsIgnoreCase("name"))
        {
            pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,user);
        }
        
        else if(contactSearchForm.getField().equalsIgnoreCase("email"))
        {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,user);
        }
        
        else if(contactSearchForm.getField().equalsIgnoreCase("phone"))
        {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy, direction,user);
        }

        logger.info("pageContact : {}",pageContact);
        
        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", contactSearchForm);
        
        return "user/search";
    }
    
    
    // delete contact

    @RequestMapping("/delete/{contactId}")    
    public String  deleteContact(@PathVariable("contactId") String contactId,HttpSession session)
    {
        contactService.delete(contactId);
        logger.info("Contact deleted with id : {}", contactId);



        session.setAttribute("message", Message.builder()
                .content("Conact is deleted successfully !! ")
                .type(MessageType.green)
                .build()
        );  
        
    
        
        return "redirect:/user/contacts";
    }
    
    
}
