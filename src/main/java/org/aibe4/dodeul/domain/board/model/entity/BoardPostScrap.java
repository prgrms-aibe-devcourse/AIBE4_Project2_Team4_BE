package org.aibe4.dodeul.domain.board.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aibe4.dodeul.domain.common.model.entity.BaseEntity;

@Entity
@Table(
        name = "board_post_scraps",
        uniqueConstraints = @UniqueConstraint(columnNames = {"board_post_id", "member_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardPostScrap extends BaseEntity {

    @Column(name = "board_post_id", nullable = false)
    private Long boardPostId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Builder
    public BoardPostScrap(Long boardPostId, Long memberId) {
        this.boardPostId = boardPostId;
        this.memberId = memberId;
    }
}
