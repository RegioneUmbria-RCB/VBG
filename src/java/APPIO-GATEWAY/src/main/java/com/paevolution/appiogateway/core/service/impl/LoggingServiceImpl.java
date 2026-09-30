package com.paevolution.appiogateway.core.service.impl;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.paevolution.appiogateway.core.service.LoggingService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoggingServiceImpl implements LoggingService {

    @Override
    public void logHTTPRequest(HttpServletRequest httpServletRequest, Object body) {

	StringBuilder stringBuilder = new StringBuilder();
	Map<String, String> parameters = buildParametersMap(httpServletRequest);
	stringBuilder.append("REQUEST ");
	stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
	stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
	stringBuilder.append("headers=[").append(buildHeadersMap(httpServletRequest)).append("] ");
	if (!parameters.isEmpty()) {
	    stringBuilder.append("parameters=[").append(parameters).append("] ");
	}
	if (body != null) {
	    stringBuilder.append("body=[" + body + "]");
	}
	// log.info(stringBuilder.toString());
    }

    @Override
    public void logHTTPResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {

	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("RESPONSE ");
	stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
	stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
	stringBuilder.append("responseHeaders=[").append(buildHeadersMap(httpServletResponse)).append("] ");
	stringBuilder.append("responseBody=[").append(body).append("]");
	// log.info(stringBuilder.toString());
    }

    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {

	Map<String, String> parametersMapCreated = new HashMap<>();
	Enumeration<String> parametersNames = httpServletRequest.getParameterNames();
	while (parametersNames.hasMoreElements()) {
	    String key = parametersNames.nextElement();
	    String value = httpServletRequest.getParameter(key);
	    parametersMapCreated.put(key, value);
	}
	return parametersMapCreated;
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest httpServletRequest) {

	Map<String, String> headersMapCreated = new HashMap<>();
	Enumeration<String> headersNames = httpServletRequest.getHeaderNames();
	while (headersNames.hasMoreElements()) {
	    String key = headersNames.nextElement();
	    String value = httpServletRequest.getParameter(key);
	    headersMapCreated.put(key, value);
	}
	return headersMapCreated;
    }

    private Map<String, String> buildHeadersMap(HttpServletResponse httpServletResponse) {

	Map<String, String> headersMapCreated = new HashMap<>();
	Collection<String> headerNames = httpServletResponse.getHeaderNames();
	for (String header : headerNames) {
	    headersMapCreated.put(header, httpServletResponse.getHeader(header));
	}
	return headersMapCreated;
    }
}
