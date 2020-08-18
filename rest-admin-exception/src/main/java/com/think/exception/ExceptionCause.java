package com.think.exception;


import com.think.common.web.R;

public interface ExceptionCause<T extends Exception> {

    /**
     * 创建异常
     *
     * @return
     */
    T exception(Object... args);

    R result();

}
