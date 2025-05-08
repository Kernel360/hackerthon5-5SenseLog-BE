package com.kernel.sense_log.common.exception;

public class DuplicateEmailException extends BaseException {
    public DuplicateEmailException() {
        super(ResultType.CONFLICT);
    }

    public DuplicateEmailException(String message) {
        super(ResultType.CONFLICT, message != null ? message : "이미 존재하는 이메일입니다.");
    }
}
