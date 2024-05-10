package com.scme.messenger.services.impl;

import com.scme.messenger.encryption.IRSAKeysGenerator;
import com.scme.messenger.encryption.util.KeyPairUtil;
import com.scme.messenger.model.KeyPair;
import com.scme.messenger.model.User;
import com.scme.messenger.services.IKeyPairService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IKeyPairServiceImpl implements IKeyPairService {

    private final IRSAKeysGenerator irsaKeysGenerator;

    @Override
    public KeyPair save(User user) {
        KeyPairUtil keyPairUtil = irsaKeysGenerator.generateKeys();

        KeyPair key = KeyPair.builder()
                .publicKey(keyPairUtil.getPublicKey().getE())
                .privateKey(keyPairUtil.getPrivateKey().getD())
                .mod(keyPairUtil.getPrivateKey().getN())
//                .user(user)
                .build();

        return null;
    }
}
