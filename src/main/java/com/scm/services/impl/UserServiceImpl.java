package com.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.controllers.repositories.UserRepo;
import com.scm.entities.User;
import com.scm.helper.ResourceNotFoundException;
import com.scm.services.UserService;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepo userRepo;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {

        // User id : have to genate

        String userid = UUID.randomUUID().toString();
        user.setUserId(userid);

        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {

        User user2 = userRepo.findById(user.getUserId()).orElseThrow(()->new ResourceNotFoundException("User not found"));
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setAbout(user.getAbout());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnable(user.isEnable());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());

        // Save the user in database

        User saveduser = userRepo.save(user2);

        return Optional.ofNullable(saveduser);


    }

    @Override
    public void deleteUser(String id) 
    {
        
        User user2 = userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));

        userRepo.delete(user2);
    }

    @Override
    public boolean isUserExist(String userId) {
        
        User user2 = userRepo.findById(userId).orElse(null);
        return user2 !=null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return user!=null ? true:false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    

}
