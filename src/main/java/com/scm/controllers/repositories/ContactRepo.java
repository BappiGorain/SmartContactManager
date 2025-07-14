package com.scm.controllers.repositories;

import org.springframework.stereotype.Repository;

import com.scm.entities.Contact;
import com.scm.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


@Repository
public interface ContactRepo extends JpaRepository<Contact,String>
{

    
    // custom finder method
    List<Contact> findByUser(User user);

    // Custom query method
    @Query("SELECT c FROM Contact c WHERE c.user.id=:userId")
    List<Contact> findByUserId(@Param("userId") String userId);

}
