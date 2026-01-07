package org.aibe4.dodeul.global.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.aibe4.dodeul.global.dto.enums.ErrorCode;
import org.aibe4.dodeul.global.dto.enums.SuccessCode;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"code", "message", "data"})
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;

    /**
     * [성공 응답]
     *
     * @param successCode 성공 코드 Enum
     * @param data 반환할 데이터 (없으면 null)
     */
    public static <T> ApiResponse<T> success(SuccessCode successCode, T data) {
        return new ApiResponse<>(
                successCode.getHttpStatus().value(), successCode.getMessage(), data);
    }

    /**
     * [실패 응답]
     *
     * @param errorCode 에러 코드 Enum 실패 시에는 보통 data가 없으므로 null로 처리
     */
    public static <T> ApiResponse<T> fail(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getHttpStatus().value(), errorCode.getMessage(), null);
    }
}
