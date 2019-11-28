package com.sap.internship.web.calculator.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sap.internship.core.PostfixCalculator;
import com.sap.internship.exception.CalculationException;
import com.sap.internship.exception.ContentException;
import com.sap.internship.web.calculator.adapter.CalculatorAdapter;
import com.sap.internship.web.calculator.dto.CalculationDTO;
import com.sap.internship.web.database.Calculation;
import com.sap.internship.web.database.CalculationsDAO;
import com.sap.internship.web.exception.RESTInvalidInputException;
import com.sap.internship.web.security.WebSecurityUtil;;

@RestController
public class CalculatorController implements CalculatorAdapter {

	private CalculationsDAO calculationsRepository;
	private PostfixCalculator calculator;

	@Autowired
	private CalculatorController(PostfixCalculator calculator, CalculationsDAO calculationsRepository) {
		this.calculator = calculator;
		this.calculationsRepository = calculationsRepository;
	}

	@GetMapping("/calculator/api/v1/evaluate")
	@Override
	public ResponseEntity<CalculationDTO> evaluate(@RequestParam String expression)
			throws RESTInvalidInputException {
		Double result = null;
		HttpStatus status = HttpStatus.BAD_REQUEST;
		try {
			result = calculator.evaluate(expression);
			status = HttpStatus.OK;
			return new ResponseEntity<CalculationDTO>(new CalculationDTO(expression, result, timestamp()),
					HttpStatus.OK);
		} catch (ContentException contentException) {
			throw new RESTInvalidInputException("Expression content is invalid.");
		} catch (CalculationException calculationException) {
			throw new RESTInvalidInputException("Attempt for invalid calculation.");
		} finally {
			calculationsRepository.insert(
					new Calculation(WebSecurityUtil.currentUser(), status.value(), expression, result, timestamp()));
		}
	}

	@GetMapping("/calculator/api/v1/history")
	public List<CalculationDTO> getHistory() {
		return convert(calculationsRepository.getHistory(WebSecurityUtil.currentUser()));
	}

	private List<CalculationDTO> convert(List<Calculation> calculationsTable) {
		List<CalculationDTO> calculationResponses = new ArrayList<CalculationDTO>();
		for (Calculation calculation : calculationsTable) {
			calculationResponses.add(new CalculationDTO(calculation.getExpression(), calculation.getResult(),
					calculation.getTimestamp()));
		}
		return calculationResponses;
	}

	private Timestamp timestamp() {
		return new Timestamp(new Date().getTime());
	}
}
