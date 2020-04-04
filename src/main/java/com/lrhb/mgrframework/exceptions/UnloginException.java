package com.lrhb.mgrframework.exceptions;

import com.lrhb.mgrframework.beans.error.IErrCode;

/**
 * 未登录异常
 */
public class UnloginException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errorCode;

    public UnloginException() {
    }

    /**
     * 构造函数
     * */
    public UnloginException(IErrCode e) {
        super(e.getDesc());
        this.errorCode = e.getCode();
    }

    /**
     * 构造函数
     * */
    public  UnloginException(String errorCode,String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public UnloginException(String message) {
        super(message);
    }

    public UnloginException(Throwable cause) {
        super(cause);
    }

    public UnloginException(String message, Throwable cause) {
        super(message, cause);
    }

}