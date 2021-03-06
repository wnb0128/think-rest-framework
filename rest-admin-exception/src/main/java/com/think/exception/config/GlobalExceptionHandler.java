package com.think.exception.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.netflix.client.ClientException;
import com.think.common.web.R;
import com.think.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * @author think
 * @desc 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${project.package.prefix:com.think}")
    protected String packagePrefix;

    @Value("${spring.application.name}")
    protected String moduleName;

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder 绑定器
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 业务异常处理
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public R businessExceptionHandler(HttpServletRequest req, Exception e) {
        BusinessException businessException = (BusinessException) e;
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<StackTraceElement> localStackTrack = new ArrayList<>();
        StringBuffer showMessage = new StringBuffer();
        if (ArrayUtils.isNotEmpty(stackTrace)) {
            for (StackTraceElement stackTraceElement : stackTrace) {
                String className = stackTraceElement.getClassName();
                int lineNumber = stackTraceElement.getLineNumber();
                if (className.startsWith(packagePrefix)) {
                    localStackTrack.add(stackTraceElement);
                    showMessage.append(className + "(" + lineNumber + ")\n");
                }
            }
            log.error("业务异常:" + e.getMessage() + "\n" + showMessage);
        } else {
            log.error("业务异常,没有调用栈 " + e.getMessage());
        }
        saveException(businessException, ExceptionLevel.business);
        return businessException.getResultEntity();
    }

    /**
     * 网络或资源找不到
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = IOException.class)
    public R ioException(IOException e) {
        log.error("网络连接错误", e);
        saveException(e, ExceptionLevel.normal);
        return SystemMessage.NETWORK_ERROR.result();
    }

    /**
     * 绑定参数异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public R bindException(BindException ex) {
        // ex.getFieldError():随机返回一个对象属性的异常信息。如果要一次性返回所有对象属性异常信息，则调用ex.getAllErrors()
        FieldError fieldError = ex.getFieldError();
        StringBuilder sb = new StringBuilder();
        sb.append(fieldError.getField())
                .append("=[").append(fieldError.getRejectedValue()).append("]")
                .append(fieldError.getDefaultMessage());
        saveException(ex, ExceptionLevel.business);
        return R.error(SystemMessage.ARGS_NULL.getCode(), sb.toString());
    }

    @ExceptionHandler(value = PersistenceException.class)
    public R persistenceException(PersistenceException e, HttpServletRequest request) {
        Throwable cause = e.getCause();
        if (cause instanceof BusinessException) {
            return businessExceptionHandler(request, e);
        }
        saveException(e, ExceptionLevel.fatal);
        log.error("数据存储出错", e);
        return SystemMessage.SAVE_ERROR.result();
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public R methodNotSupport(HttpRequestMethodNotSupportedException e) {
        log.error("Http请求异常", e);
        return SystemMessage.ACCESS_DENIED.result(e.getMessage());
    }

    @ExceptionHandler(value = JsonParseException.class)
    public R jsonParseError(JsonParseException e) {
        log.error("json格式有误", e);
        return SystemMessage.JSON_PARSE_ERROR.result(e.getMessage());
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public R mediaTypeNotSupported(HttpMediaTypeNotSupportedException e) {
        log.error("不支持的数据传输", e);
        return SystemMessage.NOT_SUPPORT_TYPE.result(e.getMessage());
    }

    @ExceptionHandler(value = ValidateException.class)
    public R validateException(ValidateException e) {
        log.error("数据校验异常", e);
        saveException(e, ExceptionLevel.normal);
        return R.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public R handleServiceException(ConstraintViolationException e) {
        log.error("参数验证失败", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuffer buf = new StringBuffer();
        for (ConstraintViolation<?> violation : violations) {
            buf.append(violation.getMessage()).append(",");
        }
        return R.error(HttpStatus.BAD_REQUEST.value(), buf.deleteCharAt(buf.length() - 1).toString());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            sb.append(error.getDefaultMessage() + ",");
        }
        return R.error(HttpStatus.BAD_REQUEST.value(), sb.length() > 0 ? sb.deleteCharAt(sb.length() - 1).toString() : "参数异常！");
    }

    @ExceptionHandler(ServiceException.class)
    public R handleServiceException(ServiceException e) {
        return R.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(ClientException.class)
    public R handleClientException(ClientException e) {
        return R.error(e.getErrorCode(), e.getErrorMessage());
    }

    /**
     * 未识别异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public R otherException(Exception e) {
        log.error("后台服务异常", e);
        saveException(e, ExceptionLevel.fatal);
        return SystemMessage.SERVICE_CALL_FAIL.result();
    }

    /**
     * 异常保存，换成elk分析日志文件
     *
     * @param e
     * @param level
     */
    protected void saveException(Exception e, ExceptionLevel level) {

    }

}
