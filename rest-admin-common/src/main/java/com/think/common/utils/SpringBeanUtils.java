package com.think.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * FileName    : SpringBeanUtils.java.java
 *
 * @author : think
 * @version : 1.0
 * Create Date : 2019年6月13日 下午8:40:37
 **/
@Component
public class SpringBeanUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        applicationContext = context;
    }

    public static <T> T getBean(String id, Class<T> tClass) {
        return applicationContext.getBean(id, tClass);
    }

    public static <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }
}  
