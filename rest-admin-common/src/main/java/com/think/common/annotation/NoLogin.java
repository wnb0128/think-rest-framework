package com.think.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author think
 * @Title NoLogin.java
 * @Description 登陆校验注解 无需登录
 * @date 2020年8月17日 下午2:39:08
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoLogin {

    boolean value() default true;

}

