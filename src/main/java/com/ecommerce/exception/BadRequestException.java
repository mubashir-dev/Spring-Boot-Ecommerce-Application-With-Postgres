package com.ecommerce.exception;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
