package com.scm.entities;



import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Contact
{
    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;
    @Column(length = 1000)
    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedIn;

    //private List<SocialLink> socialLinks = new ArrayList<>();

    @ManyToOne
    private User user;


    @OneToMany(mappedBy = "contact",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    private List<SocialLink> links = new ArrayList<>();

}

