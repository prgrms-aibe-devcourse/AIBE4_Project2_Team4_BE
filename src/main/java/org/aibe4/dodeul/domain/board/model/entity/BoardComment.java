package org.aibe4.dodeul.domain.board.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aibe4.dodeul.domain.common.model.entity.BaseEntity;

@Entity
@Table(name = "board_comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardComment extends BaseEntity {

    @Column(name = "comment_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "board_post_id", nullable = false)
    private Long boardPostId;

    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    @Column(name = "root_comment_id")
    private Long rootCommentId;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "comment_status", nullable = false, length = 20)
    private CommentStatus commentStatus;

    @Column(name = "like_count", nullable = false)
    private Long likeCount;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public BoardComment(
            Long memberId,
            Long boardPostId,
            Long parentCommentId,
            Long rootCommentId,
            String content) {
        this.memberId = memberId;
        this.boardPostId = boardPostId;
        this.parentCommentId = parentCommentId;
        this.rootCommentId = rootCommentId;
        this.content = content;
        this.commentStatus = CommentStatus.PUBLISHED;
        this.likeCount = 0L;
    }

    // ===== 비즈니스 로직 =====

    /** 댓글 수정 */
    public void update(String content) {
        validateNotDeleted();
        this.content = content;
    }

    /** 소프트 삭제 */
    public void softDelete() {
        if (this.commentStatus == CommentStatus.DELETED) return; // 멱등
        this.commentStatus = CommentStatus.DELETED;
        this.deletedAt = LocalDateTime.now();
    }

    /** 좋아요 증가 */
    public void increaseLikeCount() {
        validateNotDeleted();
        this.likeCount += 1;
    }

    /** 좋아요 감소 */
    public void decreaseLikeCount() {
        validateNotDeleted();
        if (this.likeCount > 0) this.likeCount -= 1;
    }

    /** 삭제 여부 검증 */
    private void validateNotDeleted() {
        if (this.commentStatus == CommentStatus.DELETED) {
            throw new IllegalStateException("삭제된 댓글입니다.");
        }
    }
}
