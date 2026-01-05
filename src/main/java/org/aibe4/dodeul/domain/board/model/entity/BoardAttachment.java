// **후순위** 2주차

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

    @Column(name = "board_post_id", nullable = false)
    private Long boardPostId;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_size")
    private Long fileSize;

    @Builder
    public BoardAttachment(
            Long boardPostId, String fileUrl, String fileName, String fileType, Long fileSize) {
        this.boardPostId = boardPostId;
        this.fileUrl = fileUrl;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }
}
