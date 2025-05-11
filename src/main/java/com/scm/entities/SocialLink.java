package com.scm.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SocialLink
{
    @Id
    private long id;
    private String link;
    private String title;

    @ManyToOne
    private Contact contact;
}
