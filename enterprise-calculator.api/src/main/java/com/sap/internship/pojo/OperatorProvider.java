package com.sap.internship.pojo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.sap.internship.pojos.arithmetic.operator.Addition;
import com.sap.internship.pojos.arithmetic.operator.Division;
import com.sap.internship.pojos.arithmetic.operator.Multiplication;
import com.sap.internship.pojos.arithmetic.operator.Subtraction;
import com.sap.internship.pojos.bracket.ClosingBracket;
import com.sap.internship.pojos.bracket.OpeningBracket;

public class OperatorProvider {
	private static final Map<Character, Operator> SUPPORTED_OPERATORS;
	
	static {
		SUPPORTED_OPERATORS = new HashMap<>();
		Addition addition = new Addition();
		SUPPORTED_OPERATORS.put(addition.getChar(), addition);//TODO
		Subtraction subtraction = new Subtraction();
		SUPPORTED_OPERATORS.put(subtraction.getChar(), subtraction);
		Multiplication multiplication = new Multiplication();
		SUPPORTED_OPERATORS.put(multiplication.getChar(), multiplication);
		Division division = new Division();
		SUPPORTED_OPERATORS.put(division.getChar(), division);
		OpeningBracket openingBracket = new OpeningBracket();
		SUPPORTED_OPERATORS.put(openingBracket.getChar(), openingBracket);
		ClosingBracket closingBracket = new ClosingBracket();
		SUPPORTED_OPERATORS.put(closingBracket.getChar(), closingBracket);
	}
	
	public static Operator getObject(char operator) {
		return SUPPORTED_OPERATORS.get(operator);
	}
	
	public static Set<Character> getSupportedOperators() {
		return SUPPORTED_OPERATORS.keySet();
	}
}
