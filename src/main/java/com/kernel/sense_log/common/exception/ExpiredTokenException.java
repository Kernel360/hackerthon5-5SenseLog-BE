package com.kernel.sense_log.common.exception;

public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException() {
        super("만료된 JWT 토큰입니다.");
    }
}