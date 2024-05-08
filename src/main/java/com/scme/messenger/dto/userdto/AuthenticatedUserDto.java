package com.scme.messenger.dto.userdto;

import com.scme.messenger.encryption.util.PrivateKey;
import com.scme.messenger.encryption.util.PublicKey;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUserDto {
    private String userId;

    private String name;

    private String email;

    private PublicKey publicKey;

    private PrivateKey privateKey;

    private Integer role;
}
