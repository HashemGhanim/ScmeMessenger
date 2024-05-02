package com.scme.messenger.dto.userdto;


import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlockUserDto {
    @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits")
    private String senderId;
    @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits")
    private String recepientId;
}
