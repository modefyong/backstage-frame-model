package com.lrhb.mgrframework.annotation;

import com.lrhb.mgrframework.beans.constant.OperType;

import java.lang.annotation.*;

/**
 * 业务操作注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizOperLog {
    /**操作类型*/
    OperType operType() default OperType.Query;

    /**备注*/
    String memo() default "";
}
