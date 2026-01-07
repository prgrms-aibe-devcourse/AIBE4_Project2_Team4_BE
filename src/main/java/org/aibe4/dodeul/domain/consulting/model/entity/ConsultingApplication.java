package org.aibe4.dodeul.domain.consulting.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aibe4.dodeul.domain.common.model.entity.BaseEntity;
import org.aibe4.dodeul.domain.common.model.entity.SkillTag;
import org.aibe4.dodeul.domain.consulting.model.enums.ConsultingTag;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "consulting_applications")
public class ConsultingApplication extends BaseEntity {

    @Column(name = "mentee_id", nullable = false)
    private Long menteeId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "consulting_tag", nullable = false)
    private ConsultingTag consultingTag;

    @ManyToMany
    @JoinTable(
        name = "application_skill_tags",       // 팀원이 보여준 ERD 테이블 이름
        joinColumns = @JoinColumn(name = "ticket_id"),      // 내 ID (신청서)
        inverseJoinColumns = @JoinColumn(name = "skill_tag_id") // 상대방 ID (스킬태그)
    )
    private List<SkillTag> skillTags = new ArrayList<>();

    // [수정됨] columnDefinition = "TEXT" 추가 (긴 URL 저장 가능)
    @Column(name = "file_url", columnDefinition = "TEXT")
    private String fileUrl;

    @Builder
    public ConsultingApplication(
        Long menteeId,
        String title,
        String content,
        ConsultingTag consultingTag,
        List<SkillTag> skillTags, // 빌더에도 추가
        String fileUrl) {
        this.menteeId = menteeId;
        this.title = title;
        this.content = content;
        this.consultingTag = consultingTag;
        this.skillTags = skillTags != null ? skillTags : new ArrayList<>();
        this.fileUrl = fileUrl;
    }
}
