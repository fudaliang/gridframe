package com.softcloud.grid.common.exception;

import com.softcloud.grid.common.utils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(Exception.class)
    R exception(Exception e) {
        return R.error(500, e.getMessage());
    }
}
