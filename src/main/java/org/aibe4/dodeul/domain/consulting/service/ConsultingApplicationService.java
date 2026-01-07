package org.aibe4.dodeul.domain.consulting.service;

import lombok.RequiredArgsConstructor;
import org.aibe4.dodeul.domain.consulting.dto.ConsultingApplicationRequest;
import org.aibe4.dodeul.domain.consulting.model.entity.ConsultingApplication;
import org.aibe4.dodeul.domain.consulting.repository.ConsultingApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultingApplicationService {

    private final ConsultingApplicationRepository consultingApplicationRepository;

    @Transactional
    public Long saveApplication(ConsultingApplicationRequest request) {

        ConsultingApplication application =
            ConsultingApplication.builder()
                .menteeId(request.getMenteeId())
                .title(request.getTitle())
                .content(request.getContent())
                .consultingTag(request.getConsultingTag())

                // [추가됨] 여기가 핵심입니다! DTO에서 태그를 꺼내 엔티티에 넣습니다.
                // 주의: DTO(ConsultingApplicationRequest)에도 getTechTags()가 있어야 합니다.
                .techTags(request.getTechTags())

                .fileUrl(request.getFileUrl()) // 긴 URL도 여기로 그냥 넘기면 됩니다.
                .build();

        ConsultingApplication savedApplication = consultingApplicationRepository.save(application);

        return savedApplication.getId();
    }
}
