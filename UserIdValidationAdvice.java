package com.movie.movieinfo.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class UserIdValidationAdvice extends ResponseEntityExceptionHandler implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserIdNotEmptyInterface.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String userId = webRequest.getParameter("userId");
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("userId parameter is missing or empty");
        }
        return userId;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleUserIdEmptyException(IllegalArgumentException ex, WebRequest request) {
        String bodyOfResponse = "Error: " + ex.getMessage();
        return ResponseEntity.badRequest().body(bodyOfResponse);
    }
}