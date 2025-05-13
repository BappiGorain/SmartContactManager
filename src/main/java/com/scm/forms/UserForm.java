package com.scm.forms;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm
{
    @NotBlank(message = "Username is required")
    @Size(min=3,message = "min 3 character is required")
    private String name;
    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message="password is required")
    @Size(min=6,message = "min 6 character is required")
    private String password;
    @NotBlank(message = "about must not be blank")
    private String about;
    @Size(min=10,max=12,message = "Invalid number")
    private String phoneNumber;
}
