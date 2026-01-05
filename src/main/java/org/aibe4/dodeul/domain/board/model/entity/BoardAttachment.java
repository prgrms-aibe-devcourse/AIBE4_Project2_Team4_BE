package org.aibe4.dodeul.domain.board.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aibe4.dodeul.domain.common.model.entity.BaseEntity;

@Entity
@Table(name = "board_attachments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardAttachment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_post_id")
    private BoardPost boardPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_comment_id")
    private BoardComment boardComment;

    @Column(name = "file_url", nullable = false, length = 2048)
    private String fileUrl;

    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @Column(name = "file_type", length = 50)
    private String fileType;

    @Column(name = "file_size")
    private Long fileSize;

    @Builder
    public BoardAttachment(
            BoardPost boardPost,
            BoardComment boardComment,
            String fileUrl,
            String fileName,
            String fileType,
            Long fileSize) {
        this.boardPost = boardPost;
        this.boardComment = boardComment;
        this.fileUrl = fileUrl;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    @PrePersist
    @PreUpdate
    private void validateXorConstraint() {
        if ((boardPost == null && boardComment == null)
                || (boardPost != null && boardComment != null)) {
            throw new IllegalStateException(
                    "BoardAttachment must be associated with either a BoardPost or a BoardComment, but not both.");
        }
    }
}
