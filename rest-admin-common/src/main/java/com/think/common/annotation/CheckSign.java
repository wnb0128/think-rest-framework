package com.think.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author think
 * @Title CheckSign.java
 * @Description 登陆校验注解 添加注解
 * @date 2020年8月17日 下午2:39:08
 */
@NoLogin
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckSign {

}