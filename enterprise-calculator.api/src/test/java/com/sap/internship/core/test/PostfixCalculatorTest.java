package com.sap.internship.core.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sap.internship.core.PostfixCalculator;
import com.sap.internship.exception.InvalidInputException;
import com.sap.internship.interfac.IParser;
import com.sap.internship.interfac.IPostfixConverter;
import com.sap.internship.interfac.IToken;
import com.sap.internship.interfac.IValidator;
import com.sap.internship.pojo.Operand;
import com.sap.internship.pojo.TokenCollection;
import com.sap.internship.pojos.arithmetic.operator.Addition;
import com.sap.internship.pojos.arithmetic.operator.Division;
import com.sap.internship.pojos.arithmetic.operator.Multiplication;
import com.sap.internship.pojos.bracket.OpeningBracket;

public class PostfixCalculatorTest {
	@Mock
	private IParser parser;
	
	@Mock
	private IValidator validator;
	
	@Mock
	private IPostfixConverter converter;
	
	@InjectMocks
	private PostfixCalculator calculator;

	@Before
	public void createObject() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void validCase() throws InvalidInputException {
		when(parser.parse( "1.0+2.0*1.5" )).thenReturn(new TokenCollection(new ArrayList<IToken>(Arrays.asList(
												new Operand( 1.0 ),
												new Addition(),
												new Operand( 2.0 ),
												new Multiplication(),
												new Operand( 1.5 )))) );
		when(converter.convert(new TokenCollection(new ArrayList<IToken>(Arrays.asList(new Operand( 1.0 ), 
																					   new Addition(), 
																					   new Operand( 2.0 ), 
																					   new Multiplication(), 
																					   new Operand( 1.5 )))))).thenReturn(new TokenCollection(new ArrayList<IToken>(Arrays.asList(
																							   				   		new Operand( 1.0 ), 
																							   				   		new Operand( 2.0 ),
																							   				   		new Operand( 1.5 ), 
																							   				   		new Multiplication(), 
																							   				   		new Addition()))) );	
		assertEquals(4.0, calculator.evaluate( "1.0+2.0*1.5" ), 0.0 );
	}
	
	@Test(expected = InvalidInputException.class)
	public void unparsableInputShouldInvokeException() throws InvalidInputException {
		doThrow(InvalidInputException.class).when(parser).parse( "1.0#1.0" );
		calculator.evaluate( "1.0#1.0" );
	}
	
	@Test(expected = InvalidInputException.class)
	public void invalidSemanticsShouldInvokeException() throws InvalidInputException {
		when(parser.parse( "(" )).thenReturn( new TokenCollection(new ArrayList<IToken>(Arrays.asList( new OpeningBracket() ))));
		doThrow(InvalidInputException.class).when(validator).validate( new TokenCollection(new ArrayList<IToken>(Arrays.asList( new OpeningBracket() ))));
		calculator.evaluate( "(" );
	}
	
	@Test(expected = InvalidInputException.class)
	public void divisionByZeroShouldInvokeException() throws InvalidInputException {
		when(parser.parse( "1/0" )).thenReturn( new TokenCollection(new ArrayList<IToken>(Arrays.asList(new Operand( 1.0 ), 
																						 				new Division(), 
																						 				new Operand( 0.0 )))) );
		when(converter.convert(new TokenCollection(new ArrayList<IToken>(Arrays.asList(new Operand( 1.0 ),
																					    new Division(), 
																					    new Operand( 0.0 ))))) ).
		            thenReturn(new TokenCollection(new ArrayList<IToken>(Arrays.asList(
		            		new Operand( 1.0 ), 
							new Operand( 0.0 ), 
							new Division()))));
		calculator.evaluate( "1/0" );
	}
}
