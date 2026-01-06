// src/main/java/org/aibe4/dodeul/domain/board/model/dto/ConsultingTagDto.java
package org.aibe4.dodeul.domain.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** 상담분야 DTO (현재는 이름만 사용) */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultingTagDto {

    private String name;
}
