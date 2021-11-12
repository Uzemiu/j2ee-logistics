package com.example.logistics.exception.handler;

import com.example.logistics.exception.BadRequestException;
import com.example.logistics.exception.ForbiddenException;
import com.example.logistics.exception.UnauthenticatedException;
import com.example.logistics.model.support.BaseResponse;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public BaseResponse<?> validExceptionHandler(BindException exception) {
        return BaseResponse.error(exception.getAllErrors().get(0).getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<?> validExceptionHandler(MethodArgumentNotValidException exception) {
        return BaseResponse.error(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BadRequestException.class,
            IllegalArgumentException.class,
            MultipartException.class})
    public BaseResponse<?> badRequestExceptionHandler(Exception exception){
        return BaseResponse.error(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalException.class)
    public BaseResponse<?> internalExceptionHandler(InternalException exception){
        String msg = exception.getMessage() + "\n" + Arrays.toString(exception.getStackTrace());
        return new BaseResponse<>(500, msg, null);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public BaseResponse<?> forbiddenExceptionHandler(ForbiddenException exception){
        return new BaseResponse<>(403, exception.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthenticatedException.class)
    public BaseResponse<?> internalExceptionHandler(UnauthenticatedException exception){
        return new BaseResponse<>(401, exception.getMessage(), null);
    }
}