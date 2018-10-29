package com.elearningbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LoginDto {
    @NotBlank(message = "Username is required!")
    private String username;
    @NotBlank(message = "Username is required!")
    @Length(min = 6, max = 30, message = "Password has to be between 6-30 characters.")
    private String password;
    private boolean remember;
}
