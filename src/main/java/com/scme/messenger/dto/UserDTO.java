package com.scme.messenger.dto;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserDTO {

    @Pattern(regexp = "(^$|[0-9]{8})", message = "User ID must be 8 digits")
    private String userId;

    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 5, max = 30, message = "The length of the User name should be between 5 and 30")
    private String name;

    @Email(message = "Email address should be a valid value")
    private String email;

}
