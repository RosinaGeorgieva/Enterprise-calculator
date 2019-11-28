package com.sap.internship.web.exception.handler;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sap.internship.web.calculator.dto.ErrorDTO;
import com.sap.internship.web.exception.RESTInvalidInputException;

@ControllerAdvice
public class WebCalculatorExceptionHandler extends ResponseEntityExceptionHandler  {
	
	@ExceptionHandler(RESTInvalidInputException.class)
	public ResponseEntity<ErrorDTO> handleContentException(Exception exception, WebRequest request) throws IOException {
		ErrorDTO error = new ErrorDTO();
		error.setTimestamp(new Timestamp(new Date().getTime()));
		error.setError(exception.getMessage());
		return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
	}
}
