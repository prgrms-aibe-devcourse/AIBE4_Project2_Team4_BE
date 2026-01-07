package org.aibe4.dodeul.domain.consulting.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aibe4.dodeul.domain.common.model.entity.BaseEntity;
import org.aibe4.dodeul.domain.consulting.model.enums.ConsultingTag;

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

    // [추가됨] 아까 와이어프레임 보고 추가하기로 한 '스킬 태그'
    @Column(name = "tech_tags")
    private String techTags;

    // [수정됨] columnDefinition = "TEXT" 추가 (긴 URL 저장 가능)
    @Column(name = "file_url", columnDefinition = "TEXT")
    private String fileUrl;

    @Builder
    public ConsultingApplication(
        Long menteeId,
        String title,
        String content,
        ConsultingTag consultingTag,
        String techTags, // 빌더에도 추가
        String fileUrl) {
        this.menteeId = menteeId;
        this.title = title;
        this.content = content;
        this.consultingTag = consultingTag;
        this.techTags = techTags; // 빌더에도 추가
        this.fileUrl = fileUrl;
    }
}
