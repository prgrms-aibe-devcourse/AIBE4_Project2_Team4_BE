// src/main/java/org/aibe4/dodeul/domain/board/model/dto/BoardPostListResponse.java
package org.aibe4.dodeul.domain.board.model.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** 게시글 목록 아이템 DTO */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardPostListResponse {

    private Long postId;
    private ConsultingTagDto consultingTag;
    private String title;
    private String postStatus;
    private long viewCount;
    private long scrapCount;
    private long commentCount;
    private LocalDateTime lastCommentedAt;
    private LocalDateTime createdAt;
    private boolean scrappedByMe;
    private List<String> skillTags;
}
