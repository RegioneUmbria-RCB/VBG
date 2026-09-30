package com.paevolution.appiogateway.exceptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.paevolution.appiogateway.connector.ioitalia.client.model.ProblemJson;
import com.paevolution.appiogateway.exceptions.model.ErrorResponse;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    // TODO: Migliorare gestione degli errori, con informazioni più dettagliate

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
	    WebRequest request) {

	List<String> details = new ArrayList<>();
	for (ObjectError error : ex.getBindingResult().getAllErrors()) {
	    details.add(error.getDefaultMessage());
	}
	Date date = new Date();
	ErrorResponse error = new ErrorResponse(date, "Validation Failed", HttpStatus.BAD_REQUEST.value(), details);
	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpBadRequestException.class)
    protected ResponseEntity<Object> handleBadRequestException(HttpBadRequestException ex, HttpServletRequest request) {

	ProblemJson problemJson = new ProblemJson();
	problemJson.setType(ServletUriComponentsBuilder.fromCurrentRequestUri().path(request.getContextPath()).build().toUri());
	problemJson.setDetail(ex.getMessage());
	problemJson.setStatus(HttpStatus.BAD_REQUEST.value());
	problemJson.setTitle("Bad Request");
	// problemJson.setInstance();
	return new ResponseEntity<>(problemJson, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MessageNotFoundException.class)
    public final ResponseEntity<Object> handleMessageNotFoundException(MessageNotFoundException ex, WebRequest request) {

	List<String> details = new ArrayList<String>();
	details.add(ex.getLocalizedMessage());
	Date date = new Date();
	ErrorResponse errorResponse = new ErrorResponse(date, "Messsage Not Found", HttpStatus.NOT_FOUND.value(), details);
	return new ResponseEntity<Object>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Object> handleInternalServerException(RuntimeException ex, WebRequest request) {

	List<String> details = new ArrayList<String>();
	details.add(ex.getLocalizedMessage());
	Date date = new Date();
	ErrorResponse errorResponse = new ErrorResponse(date, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value(), details);
	return new ResponseEntity<Object>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {

	List<String> details = new ArrayList<String>();
	details.add(ex.getMessage());
	Date date = new Date();
	ErrorResponse errorResponse = new ErrorResponse(date, "Errore Generico", HttpStatus.INTERNAL_SERVER_ERROR.value(), details);
	return new ResponseEntity<Object>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WebClientResponseRuntimeException.class)
    public final ResponseEntity<Object> handleWebClientResponseRuntimeException(WebClientResponseRuntimeException ex, WebRequest request) {

	List<String> details = new ArrayList<>();
	details.add(ex.getDetailMessage());
	Date date = new Date();
	ErrorResponse errorResponse = new ErrorResponse(date, ex.getWebClientResponseException().getStatusText(),
		ex.getWebClientResponseException().getStatusCode().value(), details);
	return new ResponseEntity<Object>(errorResponse, ex.getWebClientResponseException().getStatusCode());
    }

    @ExceptionHandler(ProblemFoundException.class)
    public final ResponseEntity<Object> handleProblemFoundException(ProblemFoundException ex, WebRequest request) {

	List<String> details = new ArrayList<>();
	details.add(ex.getErrorDetail());
	Date date = new Date();
	ErrorResponse errorResponse = new ErrorResponse(date, ex.getErrorTitle(), ex.getMessageDTO().getStatusCode(), details);
	return new ResponseEntity<Object>(errorResponse, HttpStatus.valueOf(ex.getMessageDTO().getStatusCode()));
    }

    @ExceptionHandler(DataIntegrationException.class)
    public final ResponseEntity<Object> handleDataIntegrationException(DataIntegrationException ex, WebRequest request) {

	List<String> details = new ArrayList<>();
	details.add(ex.getMessage());
	Date date = new Date();
	ErrorResponse errorResponse = new ErrorResponse(date, "INTERNAL SERVER ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value(), details);
	return new ResponseEntity<Object>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    /*
     * @Override protected ResponseEntity<Object>
     * handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders
     * headers, HttpStatus status, WebRequest request) {
     * 
     * Map<String, Object> body = new LinkedHashMap<>(); body.put("timestamp", new
     * Date()); body.put("status", status.value()); //Get all errors List<String>
     * errors = ex.getBindingResult().getFieldErrors().stream().map(x ->
     * x.getDefaultMessage()).collect(Collectors.toList()); body.put("errors",
     * errors); return new ResponseEntity<>(body, headers, status); }
     */
}
