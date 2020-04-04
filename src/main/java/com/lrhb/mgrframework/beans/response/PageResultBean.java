package com.lrhb.mgrframework.beans.response;

import java.io.Serializable;
import com.lrhb.mgrframework.beans.error.IErrCode;
import lombok.Data;

/**
 * 分页返回封装
 * */
@Data
public class PageResultBean<T> extends AbstractResult  implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 分页数据,同layui接受的参数名一样
     * */
    private T data;

    /**
     * 记录总数,同layui接受的参数名一样
     * */
    private Integer count;

    public PageResultBean() {
        super();
    }

    /**
     * 构造函数
     * */
    public PageResultBean(T data,Integer count) {
        super();
        this.data = data;
        this.count = count;
    }

    /**
     * 构造函数
     * */
    public PageResultBean(IErrCode e) {
        super();
        this.msg = e.getDesc();
        this.code = e.getCode();
    }

    public PageResultBean(Throwable e) {
        super();
        this.msg = e.toString();
        this.code = SYSTEM_FAIL;
    }

    /**
     * 构造函数
     * */
    public PageResultBean(String code,String msg) {
        super();
        this.msg = msg;
        this.code = code;
    }
}
