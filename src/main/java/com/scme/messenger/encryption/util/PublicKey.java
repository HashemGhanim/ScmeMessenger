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
public class PublicKey implements Serializable {
    private BigInteger e;
    private BigInteger n;
}
