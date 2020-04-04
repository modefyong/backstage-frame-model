package com.lrhb.mgrframework.beans.response;

//手机端返回数据类
public class AppResult {

	    private int code;
	    private String msg;
	    private String data;

	    public AppResult() {
	    }

	    public AppResult(int code, String msg) {
	        this.code = code;
	        this.msg = msg;
	    }

	    public AppResult(int code, String msg, String data) {
	        this.code = code;
	        this.msg = msg;
	        this.data = data;
	    }

	    /**
	     * 默认的成功
	     * @return
	     */
	    public AppResult success(){
	        return new AppResult(Status.SUCCESS.Code,Status.SUCCESS.msg);
	    }

	    /**
	     * 默认的失败
	     * @return
	     */
	    public AppResult error(){
	        return new AppResult(Status.FAIL.Code,Status.FAIL.msg);
	    }

	    /**
	     * 成功 + 返回的成功信息
	     * @param data
	     * @return
	     */
	    public AppResult sussess(String data){
	        return new AppResult(Status.SUCCESS.Code,Status.SUCCESS.msg,data);
	    }

	    public enum Status {
	        SUCCESS(0,"成功"),
	        FAIL(1,"失败");

	        private int Code;
	        private String msg;

	        Status(int code, String msg) {
	            Code = code;
	            this.msg = msg;
	        }
	    }
	}
