package com.scme.messenger.encryption.util;

import lombok.*;

import java.io.Serializable;
import java.math.BigInteger;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrivateKey implements Serializable {
    private BigInteger d;
    private BigInteger n;
}
