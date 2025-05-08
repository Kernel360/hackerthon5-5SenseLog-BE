package com.kernel.sense_log.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kernel.sense_log.common.api.Pagination;
import com.kernel.sense_log.common.exception.BaseException;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class ResponseDTO<T> implements Serializable {

    private ResultObject result;

    private T data;

    private Pagination pagination;

    public ResponseDTO(ResultObject result) {
        this.result = result;
    }

    public ResponseDTO(T data) {
        this.data = data;
    }

    public static <T> ResponseDTO<T> ok() {
        return new ResponseDTO<>(ResultObject.getSuccess());
    }

    public static <T> ResponseDTO<T> ok(T data) {
        return new ResponseDTO<>(ResultObject.getSuccess(), data, null);
    }

    public static <T> ResponseDTO<T> ok(T data, Pagination pagination) {
        return new ResponseDTO<>(ResultObject.getSuccess(), data, pagination);
    }

    public static <T> ResponseDTO<T> response(T data) {
        return new ResponseDTO<>(ResultObject.getSuccess(), data, null);
    }




    public ResponseDTO(BaseException ex) {
        this.result = new ResultObject(ex);
    }

}
