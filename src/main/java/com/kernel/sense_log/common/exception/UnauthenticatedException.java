package com.kernel.sense_log.common.exception;

public class UnauthenticatedException extends BaseException {
    public UnauthenticatedException() {
        super(ResultType.UNAUTHORIZED);
    }

    public UnauthenticatedException(String message) {
        super(ResultType.UNAUTHORIZED, message != null ? message : "로그인이 필요합니다.");
    }
}