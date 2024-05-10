package com.scme.messenger.services;

import com.scme.messenger.dto.group.*;

import java.util.List;

public interface IGroupMessageService {
    SenderGroupMessageDto save(GroupMessageDto groupMessageDto);

    void delete(GroupMessageIdDto messageIdDto);

    void pinMessage(GroupMessageIdPinDto groupMessageIdPinDto);

    List<GroupMessagePinResponseDto> getPinnedMessages(String userId);
}
