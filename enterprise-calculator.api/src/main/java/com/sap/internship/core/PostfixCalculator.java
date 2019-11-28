package com.sap.internship.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.sap.internship.exception.ContentException;
import com.sap.internship.exception.DivisionByZeroException;
import com.sap.internship.exception.InvalidTokenException;
import com.sap.internship.interfac.ICalculator;
import com.sap.internship.interfac.IParser;
import com.sap.internship.interfac.IPostfixConverter;
import com.sap.internship.interfac.IToken;
import com.sap.internship.interfac.IValidator;
import com.sap.internship.pojo.Operand;
import com.sap.internship.pojo.TokenCollection;
import com.sap.internship.pojos.arithmetic.operator.ArithmeticOperator;

public class PostfixCalculator implements ICalculator {
	private IParser parser;
	private IValidator validator;
	private IPostfixConverter converter;

	public PostfixCalculator(IParser parser, IValidator validator, IPostfixConverter converter) {
		this.parser = parser;
		this.validator = validator;
		this.converter = converter;
	}

	public double evaluate(String expression) throws DivisionByZeroException, ContentException {
		TokenCollection parsed = parser.parse(expression);
		validator.validate(parsed);
		return evaluatePostfix(converter.convert(parsed));
	}

	private double evaluatePostfix(TokenCollection expression) throws DivisionByZeroException, InvalidTokenException {
		Stack<Operand> stack = new Stack<>();
		List<IToken> tokenList = new ArrayList<IToken>(expression.getTokens());
		for (int i = 0; i < tokenList.size(); i++) {
			IToken token = tokenList.get(i);
			if (token instanceof Operand) {
				storeOperand(stack, token);
			} else {
				applyOperator(stack, token);
			}
		}
		return ((Operand)stack.pop()).getValue();
	}
	
	private void storeOperand(Stack<Operand> stack, IToken token) {
		stack.push((Operand) token);
	}
	
	private void applyOperator(Stack<Operand> stack, IToken token) throws DivisionByZeroException{
		Operand secondOperand = stack.pop();
		Operand firstOperand = stack.pop();
		ArithmeticOperator operator = ((ArithmeticOperator) token);
		stack.push(operator.applyOn(firstOperand, secondOperand));
	}
	
}
