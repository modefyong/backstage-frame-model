package com.lrhb.mgrframework.beans.response;

import java.io.Serializable;
import com.lrhb.mgrframework.beans.error.IErrCode;
import lombok.Data;

/**
 * 普通返回统一封装
 * */
@Data
public class ResultBean<T> extends AbstractResult implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 数据
	 * */
	private T data;

	public ResultBean() {
		super();
	}

	/**
	 * 构造函数
	 * */
	public ResultBean(T data) {
		super();
		this.data = data;
	}

	/**
	 * 构造函数
	 * */
	public ResultBean(IErrCode e) {
		super();
		this.msg = e.getDesc();
		this.code = e.getCode();
	}

	/**
	 * 构造函数
	 * */
	public ResultBean(Throwable e) {
		super();
		this.msg = e.toString();
		this.code = SYSTEM_FAIL;
	}

	/**
	 * 构造函数
	 * */
	public ResultBean(String code,String msg) {
		super();
		this.msg = msg;
		this.code = code;
	}

	@Override
	public String toString(){
		return "[code="+code+",data="+data+"]";
	}
}
