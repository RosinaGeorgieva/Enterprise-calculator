package com.sap.internship.web.calculator.adapter;

import org.springframework.http.ResponseEntity;

import com.sap.internship.web.calculator.dto.CalculationDTO;
import com.sap.internship.web.exception.RESTInvalidInputException;

public interface CalculatorAdapter {
	public ResponseEntity<CalculationDTO> evaluate(String expression) throws RESTInvalidInputException; 
}
