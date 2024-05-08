package com.scme.messenger.services;

import com.scme.messenger.dto.subscription.SubscriptionDto;

public interface ISubscriptionService {
    void subscribe(SubscriptionDto subscriptionDto);

    void unSubscribe(SubscriptionDto subscriptionDto);
}
