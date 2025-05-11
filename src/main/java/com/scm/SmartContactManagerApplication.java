package com.scm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SmartContactManagerApplication
{

	public static void main(String[] args)
	{
		ApplicationContext context = SpringApplication.run(SmartContactManagerApplication.class, args);

//		UserRepo userRepo = context.getBean(UserRepo.class);
//
//		User user = new User();
//
//		user.setUserId("1");
//		user.setName("Bappi");
//
//		User user1 = userRepo.save(user);
//
//		System.out.println(user1);

	}

}
