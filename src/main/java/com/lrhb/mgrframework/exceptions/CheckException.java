package com.lrhb.mgrframework.exceptions;

import com.lrhb.mgrframework.beans.error.IErrCode;
import lombok.Data;

/**
 * 检查异常封装
 * */
@Data
public class CheckException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String errorCode;

	/**
	 * 构造函数
	 * */
	public CheckException(String code,String message) {
		super(message);
		this.errorCode = code;
	}

	/**
	 * 构造函数
	 * */
	public CheckException(IErrCode e) {
		super(e.getDesc());
		this.errorCode = e.getCode();
	}

	public CheckException() {
	}

	public CheckException(String message) {
		super(message);
	}

	public CheckException(Throwable cause) {
		super(cause);
	}

	public CheckException(String message, Throwable cause) {
		super(message, cause);
	}

}
