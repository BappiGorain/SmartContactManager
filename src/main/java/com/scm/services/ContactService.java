package com.scm.services;


import java.util.List;

import com.scm.entities.Contact;
import com.scm.entities.User;


public interface ContactService {

    Contact save(Contact contact);

    Contact update(Contact contact);

    List<Contact> getAll();

    Contact getById(String id);

    void delete(String id);

    List<Contact> search(String name, String email,String phpneNumber);

    List<Contact> getByUserId(String userId);

    List<Contact> getByUser(User user);
    
}
