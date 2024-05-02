package com.scme.messenger.encryption;

import com.scme.messenger.encryption.util.KeyPairUtil;

public interface IRSAKeysGenerator {
    KeyPairUtil generateKeys();
}
