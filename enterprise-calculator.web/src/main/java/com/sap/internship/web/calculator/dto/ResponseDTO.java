package com.sap.internship.web.calculator.dto;

import java.sql.Timestamp;

public abstract class ResponseDTO {
	protected Timestamp timestamp;
	protected String expression;
	
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	public String getExpression() {
		return expression;
	}
	
	public void setExpression(String expression) {
		this.expression = expression;
	}
}
