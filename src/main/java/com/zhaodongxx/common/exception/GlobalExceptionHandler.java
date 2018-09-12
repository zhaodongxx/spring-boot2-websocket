package com.zhaodongxx.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * <P></P>
 *
 * @author zhaodong
 * @version v1.0
 * @since 2018/2/6 14:51
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleApiConstraintViolationException(ConstraintViolationException ex) {
        String message = "";
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            message += violation.getMessage() + ", ";
        }
        return message;
    }

}
