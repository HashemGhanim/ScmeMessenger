package com.scme.messenger.services;

import com.scme.messenger.model.KeyPair;
import com.scme.messenger.model.User;

public interface IKeyPairService {
    KeyPair save(User user);
}
