package com.scme.messenger.constants;

import java.util.Random;
import java.math.BigInteger;

public class RSA {

    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger phi;
    private BigInteger e;
    private BigInteger d;
    private Random rand = new Random();

    public RSA(BigInteger p, BigInteger q) {
        if (!p.isProbablePrime(10) || !q.isProbablePrime(10))
            throw new IllegalArgumentException("Both numbers must be prime.");
        if (p.equals(q))
            throw new IllegalArgumentException("p and q cannot be equal");

        this.p = p;
        this.q = q;
        this.n = p.multiply(q);
        this.phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        this.e = generateE(phi);
        this.d = e.modInverse(phi);
    }

    private BigInteger generateE(BigInteger phi) {
        BigInteger e = new BigInteger(phi.bitLength(), rand);
        while (!phi.gcd(e).equals(BigInteger.ONE) || e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(phi) >= 0) {
            e = new BigInteger(phi.bitLength(), rand);
        }
        return e;
    }

    public BigInteger[] getPublicKey() {
        return new BigInteger[] { this.e, this.n };
    }

    public BigInteger[] getPrivateKey() {
        return new BigInteger[] { this.d, this.n };
    }

    public byte[] encrypt(String plaintext) {
        byte[] bytes = plaintext.getBytes();
        BigInteger[] encrypted = new BigInteger[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            encrypted[i] = BigInteger.valueOf(bytes[i]).modPow(this.e, this.n);
        }

        byte[] ciphertext = new byte[encrypted.length];
        for (int i = 0; i < encrypted.length; i++) {
            ciphertext[i] = encrypted[i].byteValue();
        }
        return ciphertext;
    }

    public String decrypt(byte[] ciphertext) {
        BigInteger[] encrypted = new BigInteger[ciphertext.length];

        for (int i = 0; i < ciphertext.length; i++) {
            encrypted[i] = BigInteger.valueOf(ciphertext[i] & 0xff);
        }

        char[] plaintext = new char[encrypted.length];
        for (int i = 0; i < encrypted.length; i++) {
            BigInteger decryptedChar = encrypted[i].modPow(this.d, this.n);
            plaintext[i] = (char) decryptedChar.intValue();
        }
        return new String(plaintext);
    }
}

