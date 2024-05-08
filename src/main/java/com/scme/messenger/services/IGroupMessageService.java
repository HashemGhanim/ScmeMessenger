package com.scme.messenger.services;

import com.scme.messenger.dto.group.*;

import java.util.List;
import java.util.Set;

public interface IGroupMessageService {
    SenderGroupMessageDto save(GroupMessageDto groupMessageDto);

    void delete(GroupMessageIdDto messageIdDto);

    void pinMessage(GroupMessageIdPinDto groupMessageIdPinDto);

    List<GroupMessageResponseDto> getPinnedMessages(String userId);
}
