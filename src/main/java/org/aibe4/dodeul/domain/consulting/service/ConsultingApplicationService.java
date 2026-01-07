package org.aibe4.dodeul.domain.consulting.service;

import lombok.RequiredArgsConstructor;
import org.aibe4.dodeul.domain.common.model.entity.SkillTag;
import org.aibe4.dodeul.domain.common.repository.SkillTagRepository;
import org.aibe4.dodeul.domain.consulting.dto.ConsultingApplicationRequest;
import org.aibe4.dodeul.domain.consulting.model.entity.ConsultingApplication;
import org.aibe4.dodeul.domain.consulting.repository.ConsultingApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultingApplicationService {

    private final ConsultingApplicationRepository consultingApplicationRepository;
    private final SkillTagRepository skillTagRepository; // [추가] 태그를 찾기 위해 필요합니다.

    @Transactional
    public Long saveApplication(ConsultingApplicationRequest request) {

        // 1. 문자열 태그("Java, Spring")를 쪼개서 실제 SkillTag 객체 리스트로 변환
        List<SkillTag> skillTags = new ArrayList<>();

        if (request.getTechTags() != null && !request.getTechTags().isEmpty()) {
            skillTags = Arrays.stream(request.getTechTags().split(","))
                .map(String::trim) // 공백 제거 (" Java" -> "Java")
                .map(tagName -> skillTagRepository.findByName(tagName)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 태그입니다: " + tagName)))
                .collect(Collectors.toList());
        }

        // 2. 엔티티 생성 (리스트로 변환된 태그를 넣어줌)
        ConsultingApplication application = ConsultingApplication.builder()
            .menteeId(request.getMenteeId())
            .title(request.getTitle())
            .content(request.getContent())
            .consultingTag(request.getConsultingTag())
            .fileUrl(request.getFileUrl())
            .skillTags(skillTags) // [변경] String 대신 List<SkillTag>가 들어갑니다.
            .build();

        ConsultingApplication savedApplication = consultingApplicationRepository.save(application);

        return savedApplication.getId();
    }
}
