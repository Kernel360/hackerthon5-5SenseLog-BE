package com.kernel.sense_log.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.kernel.sense_log.common.exception.BaseException;
import com.kernel.sense_log.common.exception.ResultType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultObject implements Serializable {

    public String code;

    private LocalDateTime responseTime;

    public String desc;


    public ResultObject(ResultType resultType) {
        this.code = resultType.getCode();
        this.desc = resultType.getDesc();
        this.responseTime = LocalDateTime.now();
    }

    public ResultObject(ResultType resultCode, String message) {
        this.code = resultCode.getCode();
        this.desc = message;
        this.responseTime = LocalDateTime.now();
    }

    public ResultObject(BaseException e) {
        this.code = e.getCode();
        this.desc = e.getDesc();
        this.responseTime = LocalDateTime.now();
    }

    public static ResultObject getSuccess() {
        return new ResultObject(ResultType.OK);
    }
}
