package org.aibe4.dodeul.domain.consultation.model.dto;

import lombok.Builder;
import lombok.Getter;
import org.aibe4.dodeul.domain.consultation.model.entity.Message;

@Getter
@Builder
public class MessageDto {
    private String content;
    private String type;
    private String senderNickname;
    private boolean isMine;

    public static MessageDto of(Message message, Long memberId) {
        return MessageDto.builder()
                .content(message.getContent())
                .type(message.getMessageType().name())
                .senderNickname(message.getSender().getNickname())
                .isMine(message.getSender().getId().equals(memberId))
                .build();
    }
}
