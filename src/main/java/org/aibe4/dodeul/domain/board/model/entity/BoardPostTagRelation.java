package org.aibe4.dodeul.domain.board.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.aibe4.dodeul.domain.common.model.entity.BaseEntity;
import org.aibe4.dodeul.domain.common.model.entity.SkillTag;

@Entity
@Table(
        name = "board_post_tag_relations",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"board_post_id", "skill_tag_id"})})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardPostTagRelation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_post_id", nullable = false)
    private BoardPost boardPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_tag_id", nullable = false)
    private SkillTag skillTag;

    @Builder
    public BoardPostTagRelation(BoardPost boardPost, SkillTag skillTag) {
        this.boardPost = boardPost;
        this.skillTag = skillTag;
    }
}
