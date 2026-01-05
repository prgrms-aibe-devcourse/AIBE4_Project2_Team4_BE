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

    @Column(name = "accepted_comment_id")
    private Long acceptedCommentId;

    @Column(nullable = false, length = 255)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "board_consulting", length = 30)
    private String boardConsulting;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_status", nullable = false, length = 20)
    private PostStatus postStatus;

    @Column(name = "view_count", nullable = false)
    private Long viewCount;

    @Column(name = "scrap_count", nullable = false)
    private Long scrapCount;

    @Column(name = "comment_count", nullable = false)
    private Long commentCount;

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
        this.viewCount = 0L;
        this.scrapCount = 0L;
        this.commentCount = 0L;
    }

    /** 게시글 수정 */
    public void update(String boardConsulting, String title, String content) {
        validateNotDeleted();
        if (boardConsulting != null) this.boardConsulting = boardConsulting;
        if (title != null) this.title = title;
        if (content != null) this.content = content;
    }

    /** 게시글 종료 (OPEN → CLOSED) */
    public void close() {
        validateNotDeleted();
        if (this.postStatus == PostStatus.CLOSED) return; // 멱등
        this.postStatus = PostStatus.CLOSED;
    }

    /** 소프트 삭제 */
    public void softDelete() {
        if (this.postStatus == PostStatus.DELETED) return; // 멱등
        this.postStatus = PostStatus.DELETED;
        this.deletedAt = LocalDateTime.now();
    }

    /** 댓글 채택 (채택 시 자동 CLOSED) */
    public void acceptComment(Long commentId) {
        validateNotDeleted();
        if (this.postStatus == PostStatus.CLOSED) {
            throw new IllegalStateException("이미 종료된 게시글입니다.");
        }
        if (this.acceptedCommentId != null) {
            throw new IllegalStateException("이미 채택된 댓글이 있습니다.");
        }
        this.acceptedCommentId = commentId;
        this.postStatus = PostStatus.CLOSED;
    }

    /** 조회수 증가 */
    public void increaseViewCount() {
        validateNotDeleted();
        this.viewCount += 1;
    }

    /** 댓글 생성 시 호출 */
    public void onCommentCreated() {
        validateNotDeleted();
        this.commentCount += 1;
        this.lastCommentedAt = LocalDateTime.now();
    }

    /** 댓글 삭제 시 호출 */
    public void onCommentDeleted() {
        validateNotDeleted();
        if (this.commentCount > 0) this.commentCount -= 1;
    }

    /** 스크랩 증가 */
    public void increaseScrapCount() {
        validateNotDeleted();
        this.scrapCount += 1;
    }

    /** 스크랩 감소 */
    public void decreaseScrapCount() {
        validateNotDeleted();
        if (this.scrapCount > 0) this.scrapCount -= 1;
    }

    /** 삭제 여부 검증 */
    private void validateNotDeleted() {
        if (this.postStatus == PostStatus.DELETED) {
            throw new IllegalStateException("삭제된 게시글입니다.");
        }
    }
}
