package org.aibe4.dodeul.domain.board.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aibe4.dodeul.domain.common.model.entity.BaseEntity;

@Entity
@Table(name = "board_posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardPost extends BaseEntity {

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accepted_comment_id")
    private BoardComment acceptedComment;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "board_consulting", nullable = false, length = 30)
    private String boardConsulting;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_status", nullable = false, length = 20)
    private PostStatus postStatus;

    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

    @Column(name = "scrap_count", nullable = false)
    private Integer scrapCount;

    @Column(name = "comment_count", nullable = false)
    private Integer commentCount;

    @Column(name = "last_commented_at")
    private LocalDateTime lastCommentedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public BoardPost(Long memberId, String title, String content, String boardConsulting) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.boardConsulting = boardConsulting;
        this.postStatus = PostStatus.OPEN;
        this.viewCount = 0;
        this.scrapCount = 0;
        this.commentCount = 0;
    }

    public void update(String title, String content, String boardConsulting) {
        validateNotDeleted();
        this.title = title;
        this.content = content;
        this.boardConsulting = boardConsulting;
    }

    public void delete() {
        validateNotDeleted();
        this.postStatus = PostStatus.DELETED;
        this.deletedAt = LocalDateTime.now();
    }

    public void close() {
        validateNotDeleted();
        if (this.postStatus == PostStatus.CLOSED) {
            return;
        }
        this.postStatus = PostStatus.CLOSED;
    }

    public void acceptComment(BoardComment comment) {
        validateNotDeleted();
        if (this.acceptedComment != null) {
            throw new IllegalStateException("Comment already accepted");
        }
        this.acceptedComment = comment;
        this.close();
    }

    public void increaseViewCount() {
        validateNotDeleted();
        this.viewCount++;
    }

    public void increaseScrapCount() {
        this.scrapCount++;
    }

    public void decreaseScrapCount() {
        if (this.scrapCount > 0) {
            this.scrapCount--;
        }
    }

    public void increaseCommentCount() {
        this.commentCount++;
        this.lastCommentedAt = LocalDateTime.now();
    }

    public void decreaseCommentCount() {
        if (this.commentCount > 0) {
            this.commentCount--;
        }
    }

    private void validateNotDeleted() {
        if (this.postStatus == PostStatus.DELETED) {
            throw new IllegalStateException("Post is deleted");
        }
    }
}
