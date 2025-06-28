package com.scm.controllers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User,String>
{
    // Custom finder methods
}
