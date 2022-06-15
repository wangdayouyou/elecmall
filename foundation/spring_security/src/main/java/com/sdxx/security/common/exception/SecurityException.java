package com.sdxx.security.common.exception;

import com.sdxx.security.common.enums.SecurityHttpStatus;
import org.springframework.http.HttpStatus;

public class SecurityException extends RuntimeException{
    /**
     * http状态码
     */
    private Integer httpStatusCode;

    private Object object;


    /**
     * @param httpStatus http状态码
     */
    public SecurityException(SecurityHttpStatus httpStatus) {
        super(httpStatus.getMsg());
        this.httpStatusCode = httpStatus.value();
    }

    /**
     * @param httpStatus http状态码
     */
    public SecurityException(SecurityHttpStatus httpStatus, String msg) {
        super(msg);
        this.httpStatusCode = httpStatus.value();
    }


    public SecurityException(String msg) {
        super(msg);
        this.httpStatusCode = HttpStatus.BAD_REQUEST.value();
    }

    public SecurityException(String msg, Object object) {
        super(msg);
        this.httpStatusCode = HttpStatus.BAD_REQUEST.value();
        this.object = object;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }
}
