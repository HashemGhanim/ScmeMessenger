package com.scme.messenger.dto.userdto;

import com.scme.messenger.encryption.util.PublicKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Schema(
        name = "User Response",
        description = "Schema to hold user response information"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits")
    private String userId;

    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 5, max = 30, message = "The length of the User name should be between 5 and 30")
    private String name;

    @Email(message = "Email address should be a valid value")
    private String email;

    private PublicKey publicKey;
    @Schema(
            description = "Role of the user if 1 is DOCTOR if 0 is STUDENT"
    )
    @NotNull(message = "Role can not be a null or empty")
    private Integer role;

}
