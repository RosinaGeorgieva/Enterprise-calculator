package com.sap.internship.core;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sap.internship.exception.ContentException;
import com.sap.internship.exception.InvalidOrderException;
import com.sap.internship.interfac.IParser;
import com.sap.internship.interfac.IToken;
import com.sap.internship.pojo.Operand;
import com.sap.internship.pojo.OperatorProvider;
import com.sap.internship.pojo.TokenCollection;

public class Parser implements IParser {
	private static String SUPPORTED_OPERATORS = getSupportedOperatorsRegex();
	
	public TokenCollection parse(String expression) throws ContentException {
		return extractTokens(expression);
	}
	
	private TokenCollection extractTokens(String expression) throws InvalidOrderException {
		String regex = "(?<operand>(\\d+(\\.\\d)?\\d*))|(?<negative>(" + SUPPORTED_OPERATORS + ")(-\\d+(\\.\\d)?\\d*))" + "|" + "(?<operator>(" + SUPPORTED_OPERATORS + "(\\t|\\s)*))|(?<invalid>(.+))"; 
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(expression);
		List<IToken> tokens = new ArrayList<IToken>();
		while(matcher.find()) {
			String operand = matcher.group("operand");
			String operator = matcher.group("operator");
			String negative = matcher.group("negative");
			if (isFound(operand)) {
			 	tokens.add(new Operand(Double.parseDouble(operand)));
			} else if (isFound(negative)) { 
				tokens.add(OperatorProvider.getObject(negative.charAt(0)));
				tokens.add(new Operand(Double.parseDouble(negative.substring(1, negative.length()))));
			} else if (foundIsBracket(operator)) {
				tokens.add(OperatorProvider.getObject(operator.charAt(0)));
			} else if (isFound(operator)) {
				tokens.add(OperatorProvider.getObject(operator.charAt(0)));
			} else {
				throw new InvalidOrderException("InvalidOrderException");
			}
		}
		return new TokenCollection(tokens);
	}
	
	private static String getSupportedOperatorsRegex() {
		ArrayList<String> operators = new ArrayList<>();
		for(Character operator : OperatorProvider.getSupportedOperators()) {
			operators.add("\\" + Character.toString(operator));
		}
		return String.join("|", operators);
	}
	
	private boolean isFound(String token) {
		return token != null;
	}
	
	private boolean foundIsBracket(String token) {
		return token!= null && (token.equals( "(" ) || token.equals( ")" ));
	}
}
 