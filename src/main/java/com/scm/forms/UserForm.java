package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

    @NotBlank(message = "Username is equired")
    @Size(min = 3,message = "Min 3 character size is required")
    private String name;
    @Email(message = "Email is not valid")
    @NotBlank(message = "Email is required")
    private String email;
    @Size(min = 6, message = "Minimum 6 character is required")
    private String password;
    @NotBlank(message = "About is requried")
    private String about;
    @Size(min = 8,max = 12, message = "Invalid phone number")
    private String phoneNumber;

}
