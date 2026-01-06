package org.aibe4.dodeul.domain.board.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardPostListRequest {

    private Long consultingTagId;
    private List<Long> skillTagIds;

    // 없거나 잘못되면 OPEN
    private String status;
    private String keyword;

    // LATEST / VIEWS / ACTIVE
    private String sort;

    private Integer page;
    private Integer size;
}
