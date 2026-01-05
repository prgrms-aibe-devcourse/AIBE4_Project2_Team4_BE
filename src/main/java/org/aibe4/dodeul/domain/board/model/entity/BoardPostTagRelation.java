package org.aibe4.dodeul.domain.board.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aibe4.dodeul.domain.common.model.entity.BaseEntity;

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

    @Column(name = "skill_tag_id", nullable = false)
    private Long skillTagId;

    @Builder
    public BoardPostTagRelation(BoardPost boardPost, Long skillTagId) {
        this.boardPost = boardPost;
        this.skillTagId = skillTagId;
    }
}
