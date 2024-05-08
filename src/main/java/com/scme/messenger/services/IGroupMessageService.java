package com.scme.messenger.services;

import com.scme.messenger.dto.group.GroupMessageDto;
import com.scme.messenger.dto.group.GroupMessageIdDto;
import com.scme.messenger.dto.group.GroupMessageIdPinDto;
import com.scme.messenger.dto.group.SenderGroupMessageDto;

import java.util.Set;

public interface IGroupMessageService {
    SenderGroupMessageDto save(GroupMessageDto groupMessageDto);

    void delete(GroupMessageIdDto messageIdDto);

    void pinMessage(GroupMessageIdPinDto groupMessageIdPinDto);
}
