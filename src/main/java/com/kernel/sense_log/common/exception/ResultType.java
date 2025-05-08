package com.kernel.sense_log.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultType {

    //도메인별로 나누기
    //diary
    DIARY_NOT_FOUND("404", "다이어리를 찾을 수 없습니다."),
    //diaryLike

    //group

    //groupMember

    //user
    USER_NOT_FOUND("404", "사용자를 찾을 수 없습니다"),
    //공통
    OK("200", "success"),
    CREATED("201", "created"),
    ACCEPTED("202", "accepted"),
    NO_CONTENT("204", "no content"),

    BAD_REQUEST("400", "bad request"),
    UNAUTHORIZED("401", "unauthorized"),
    FORBIDDEN("403", "forbidden"),
    NOT_FOUND("404", "not found"),
    METHOD_NOT_ALLOWED("405", "method not allowed"),
    CONFLICT("409", "conflict"),

    INTERNAL_SERVER_ERROR("500", "internal server error"),
    NOT_IMPLEMENTED("501", "not implemented"),
    BAD_GATEWAY("502", "bad gateway"),
    SERVICE_UNAVAILABLE("503", "service unavailable");

    private final String code;
    private final String desc;
}
