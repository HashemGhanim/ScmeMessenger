package com.scme.messenger.dto.userdto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String userId;
    private String name;
    private String email;
    private Integer role;
}
