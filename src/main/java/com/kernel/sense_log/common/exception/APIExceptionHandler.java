package com.kernel.sense_log.common.exception;

import com.kernel.sense_log.common.dto.ResponseDTO;
import com.kernel.sense_log.common.dto.ResultObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class APIExceptionHandler extends RuntimeException {

    @ExceptionHandler(BaseException.class)
    protected ResponseDTO<ResultObject> handleBaseException(BaseException e,
                                                            HttpServletRequest request, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        
        // ResultType에 따라 HTTP 상태 코드 설정
        if (e.getCode() != null) {
            try {
                int statusCode = Integer.parseInt(e.getCode());
                response.setStatus(statusCode);
            } catch (NumberFormatException ex) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }
        
        return new ResponseDTO<>(e);
    }
    
    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseDTO<ResultObject> handleUnauthenticatedException(UnauthenticatedException e,
                                                                 HttpServletRequest request, HttpServletResponse response) {
        log.warn("인증 실패: {}", e.getMessage());
        return new ResponseDTO<>(e);
    }
    
    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ResponseDTO<ResultObject> handleDuplicateEmailException(DuplicateEmailException e,
                                                                  HttpServletRequest request, HttpServletResponse response) {
        log.warn("이메일 중복: {}", e.getMessage());
        return new ResponseDTO<>(e);
    }
}