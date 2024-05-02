package com.scme.messenger.encryption.util;


import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeyPairUtil implements Serializable {
    private PublicKey publicKey;
    private PrivateKey privateKey;
}
