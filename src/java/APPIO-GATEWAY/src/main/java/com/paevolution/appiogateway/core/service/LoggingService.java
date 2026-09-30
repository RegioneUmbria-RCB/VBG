package com.paevolution.appiogateway.core.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoggingService {

    void logHTTPRequest(HttpServletRequest httpServletRequest, Object body);

    void logHTTPResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body);
}
