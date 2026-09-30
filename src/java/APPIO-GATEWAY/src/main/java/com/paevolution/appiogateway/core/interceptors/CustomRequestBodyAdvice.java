package com.paevolution.appiogateway.core.interceptors;

import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import com.paevolution.appiogateway.core.service.LoggingService;

@ControllerAdvice
public class CustomRequestBodyAdvice extends RequestBodyAdviceAdapter {

    private LoggingService loggingService;
    private HttpServletRequest httpServletRequest;

    @Autowired
    public void setLoggingService(LoggingService loggingService) {

	this.loggingService = loggingService;
    }

    @Autowired
    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {

	this.httpServletRequest = httpServletRequest;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

	return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
	    Class<? extends HttpMessageConverter<?>> converterType) {

	this.loggingService.logHTTPRequest(httpServletRequest, body);
	return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
