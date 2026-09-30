package it.sgp.middleware.security.rest;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice(basePackages = "it.sgp.middleware.security.rest")
public class GlobalRestExceptionHandler {

	@ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception ex, HttpServletRequest request) {
		
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        problem.setTitle("Internal server error");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://api.soluzioni.it/problems/internal-server-error"));
        problem.setInstance(URI.create(request.getRequestURI()));
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(problem);
    }
	
}
