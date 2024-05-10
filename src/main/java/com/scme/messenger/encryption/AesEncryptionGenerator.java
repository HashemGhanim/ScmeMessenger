package com.scme.messenger.encryption;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class AesEncryptionGenerator {
    public static String generateKey() throws Exception{
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey key = keyGenerator.generateKey();

        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}
