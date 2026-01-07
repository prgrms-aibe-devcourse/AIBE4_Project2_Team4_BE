package org.aibe4.dodeul.domain.consultation.model.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.aibe4.dodeul.domain.consultation.model.entity.ConsultationRoom;
import org.aibe4.dodeul.domain.consulting.model.entity.ConsultingApplication;
import org.aibe4.dodeul.domain.matching.model.entity.Matching;
import org.aibe4.dodeul.domain.member.model.entity.Member;
import org.aibe4.dodeul.domain.member.model.enums.Role;

@Getter
@Builder
public class ConsultationRoomDto {

    private String myRole; // 현재 나의 역할 (MENTOR or MENTEE) - 프론트 처리용

    private OpponentMemberDto opponentMemberDto;
    private ConsultingApplicationDto consultingApplicationDto;
    private List<MessageDto> messageDtoList;

    public static ConsultationRoomDto of(
            ConsultationRoom room, List<MessageDto> messageDtoList, Long currentMemberId) {

        // 엔티티 조회
        Matching matching = room.getValidatedMatching();
        ConsultingApplication application = room.getValidatedApplication();

        // 상대방 정보 조회 및 내 역할 설정
        Member opponentMember = resolveOpponent(matching, currentMemberId);
        String myRole = resolveMyRole(matching, currentMemberId);

        return ConsultationRoomDto.builder()
                .myRole(myRole)
                .opponentMemberDto(OpponentMemberDto.of(opponentMember))
                .consultingApplicationDto(ConsultingApplicationDto.of(application))
                .messageDtoList(messageDtoList)
                .build();
    }

    private static Member resolveOpponent(Matching matching, Long currentMemberId) {
        if (matching.getMentor().getId().equals(currentMemberId)) {
            return matching.getMentee();
        }
        return matching.getMentor();
    }

    private static String resolveMyRole(Matching matching, Long currentMemberId) {
        if (matching.getMentor().getId().equals(currentMemberId)) {
            return Role.MENTOR.name();
        }
        return Role.MENTEE.name();
    }
}
