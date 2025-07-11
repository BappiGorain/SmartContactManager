package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactForm
{
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedInLink;
    private MultipartFile profileImage;
}
