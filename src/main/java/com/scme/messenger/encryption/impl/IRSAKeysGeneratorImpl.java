package com.scme.messenger.encryption.impl;

import com.scme.messenger.encryption.IRSAKeysGenerator;
import com.scme.messenger.encryption.util.KeyPairUtil;
import com.scme.messenger.encryption.util.PrivateKey;
import com.scme.messenger.encryption.util.PublicKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Random;


@Component
public class IRSAKeysGeneratorImpl implements IRSAKeysGenerator {

    @Value("${number.bits.keys}")
    private int numberOfBitsForKeys;

    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger phi;
    private BigInteger e;
    private BigInteger d;

    private Random random = new Random();

    @Override
    public KeyPairUtil generateKeys() {

        this.p = largePrime(512);
        this.q = largePrime(512);
        this.n = p.multiply(q);
        this.phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        this.e = generateE(phi);
        this.d = e.modInverse(phi);


        return KeyPairUtil.builder()
                .publicKey(PublicKey.builder()
                        .e(this.e)
                        .n(this.n)
                        .build())
                .privateKey(PrivateKey.builder()
                        .d(this.d)
                        .n(this.n)
                        .build())
                .build();
    }

    private BigInteger generateE(BigInteger phi){
        BigInteger e = new BigInteger(phi.bitLength() , this.random);

        while (!e.gcd(phi).equals(BigInteger.ONE) || e.compareTo(phi) > 0 || e.compareTo(BigInteger.ONE) <= 0){
            e = new BigInteger(phi.bitLength() , this.random);
        }

        return e;
    }

    private BigInteger largePrime(int bits) {
        BigInteger largePrime = BigInteger.probablePrime(bits, this.random);
        return largePrime;
    }


}
