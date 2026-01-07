package org.aibe4.dodeul.domain.consultation.model.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.aibe4.dodeul.domain.consultation.model.entity.ConsultationRoom;
import org.aibe4.dodeul.domain.consulting.model.entity.ConsultingApplication;
import org.aibe4.dodeul.domain.member.model.entity.Member;

@Getter
@Builder
public class ConsultationRoomDto {

    // 멘토 정보 -> 이거 MemberDto로 빼서 멘토, 멘티인 경우 따로 빼야할듯?
    private String mentorNickname;
    private String mentorProfileUrl;

    // 신청서 정보
    private String title;
    private String content;
    private String fileUrl;
    private String consultingTag;
    //    private List<String> skillList;

    // 메시지
    private List<MessageDto> messageDtoList;

    public static ConsultationRoomDto of(
            ConsultationRoom room,
            ConsultingApplication application,
            List<MessageDto> messageDtoList) {
        Member mentor = room.getMentor();
        String mentorNickname = (mentor != null) ? mentor.getNickname() : "(알 수 없음)";

        return ConsultationRoomDto.builder()
                .mentorNickname(mentorNickname)
                .title(application.getTitle())
                .content(application.getContent())
                .fileUrl(application.getFileUrl())
                .consultingTag(application.getConsultingTag().name())
                .messageDtoList(messageDtoList)
                .build();
    }
}
