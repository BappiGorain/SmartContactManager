package com.scm.services;

import java.util.List;
import java.util.Optional;

import com.scm.entities.User;

public interface UserService {

    User saveUser(User user);

    Optional<User> getUserById(String id);

    Optional<User> deleteUser(String id);

    boolean isUserExist(String userId);

    boolean isUserExistByEmail(String email);

    List<User> getAllUsers();

}
