package com.sap.internship.web.calculator.dto;

import java.sql.Timestamp;

public class ErrorDTO extends ResponseDTO {
	private String error;
	
	public ErrorDTO() {
		
	}
	
	public ErrorDTO(String expression, String error, Timestamp timestamp) {
		this.expression = expression;
		this.error = error;	
		this.timestamp = timestamp;
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
}
