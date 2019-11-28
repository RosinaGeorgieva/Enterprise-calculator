package com.sap.internship.core.test;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.sap.internship.core.Validator;
import com.sap.internship.exception.ContentException;
import com.sap.internship.exception.InvalidOrderException;
import com.sap.internship.interfac.IToken;
import com.sap.internship.interfac.IValidator;
import com.sap.internship.pojo.Operand;
import com.sap.internship.pojo.TokenCollection;
import com.sap.internship.pojos.arithmetic.operator.Addition;
import com.sap.internship.pojos.arithmetic.operator.Division;
import com.sap.internship.pojos.arithmetic.operator.Multiplication;
import com.sap.internship.pojos.bracket.ClosingBracket;
import com.sap.internship.pojos.bracket.OpeningBracket;

public class ValidatorTest {
	private IValidator validator;

	@Before
	public void createObject() {
		validator = new Validator();
	}
	
	@Test
	public void twoOperandsShouldBeValid() throws ContentException {
		validator.validate(new TokenCollection(new ArrayList<IToken>(Arrays.asList(new Operand( 1.0 ), 
																				   new Division(), 
																				   new Operand( 1.0 )))));
	}

	@Test(expected = InvalidOrderException.class)
	public void sequentialOperatorsShouldInvokeException() throws ContentException {
		validator.validate(new TokenCollection(new ArrayList<IToken>(Arrays.asList(new Operand( 1.0 ), 
																				   new Addition(), 
																				   new Division(), 
																				   new Operand( 1.0 )))));
	}
	
	@Test(expected = InvalidOrderException.class)
	public void endingInOperatorShouldInvokeException() throws ContentException {
		validator.validate(new TokenCollection(new ArrayList<IToken>(Arrays.asList(new Operand( 1.0 ), 
																				   new Division(), 
																				   new Operand( 1.0 ), 
																				   new Multiplication()))));
	}
	
	@Test(expected = InvalidOrderException.class)
	public void startingInOperatorShouldInvokeException() throws ContentException {
		validator.validate(new TokenCollection(new ArrayList<IToken>(Arrays.asList(new Addition(), 
																				   new Operand( 1.0 ), 
																				   new Division(), 
																				   new Operand( 1.0 )))));
	}
	
	@Test
	public void parenthesesAroundOperandShouldBeValid() throws ContentException {
		validator.validate(new TokenCollection(new ArrayList<IToken>(Arrays.asList(new OpeningBracket(), 
																				   new Operand( 1.0 ), 
																				   new ClosingBracket(), 
																				   new Division(), 
																				   new Operand( 1.0 )))));
	}
	
	@Test(expected = InvalidOrderException.class)
	public void parenthesesAroundOperatorShouldInvokeException() throws ContentException {
		validator.validate(new TokenCollection(new ArrayList<IToken>(Arrays.asList(new OpeningBracket(), 
																				   new Multiplication(), 
																				   new ClosingBracket(), 
																				   new Division(), 
																				   new Operand( 1.0 )))));
	}
	
	@Test(expected = InvalidOrderException.class)
	public void unbalancedParenthesesShouldInvokeException() throws ContentException {
		validator.validate(new TokenCollection(new ArrayList<IToken>(Arrays.asList(new OpeningBracket(), 
																				   new Operand( 1.0 ), 
																				   new Addition(), 
																				   new Operand( 1.0 ), 
																				   new Multiplication(), 
																				   new Operand( 1.0 )))));
	}
}