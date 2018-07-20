package com.travel.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by lusouza on 21/06/18.
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody ExceptionInfo internalServerError(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return new ExceptionInfo(e.getMessage(), "Unknown error.");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public @ResponseBody ExceptionInfo dataNotFound(Exception e) {
        LOGGER.warn(e.getMessage(), e);
        return new ExceptionInfo(e.getMessage(), "Data not found.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadDataException.class)
    public @ResponseBody ExceptionInfo badData(BadDataException e) {
        LOGGER.warn(e.getMessage(), e);
        return new ExceptionInfo(e.getMessage(), "Bad data.");
    }
}
