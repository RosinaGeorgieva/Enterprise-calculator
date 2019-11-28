package com.sap.internship.core.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.sap.internship.core.Parser;
import com.sap.internship.exception.ContentException;
import com.sap.internship.exception.InvalidOrderException;
import com.sap.internship.interfac.IToken;
import com.sap.internship.pojo.Operand;
import com.sap.internship.pojo.TokenCollection;
import com.sap.internship.pojos.arithmetic.operator.Addition;
import com.sap.internship.pojos.arithmetic.operator.Division;
import com.sap.internship.pojos.arithmetic.operator.Multiplication;
import com.sap.internship.pojos.bracket.ClosingBracket;
import com.sap.internship.pojos.bracket.OpeningBracket;

public class ParserTest {
	private Parser parser;

	@Before
	public void createObject() {
		parser = new Parser();
	}
	
	@Test
	public void generalCaseShouldBeParsed() throws ContentException {
		assertEquals(new TokenCollection(new ArrayList<IToken>(Arrays.asList(new Operand( 1.0 ), 
																			 new Addition(), 
																			 new Operand( 1.0 ), 
																			 new Multiplication(), 
																			 new Operand( 1.0 )))), parser.parse( "1.0+1.0*1" ));
	}
	
	@Test
	public void spacesShouldBeIgnoredWhenParsing() throws ContentException { 
		assertEquals(new TokenCollection(new ArrayList<IToken>(Arrays.asList(new Operand( 1.5 ), 
																			 new Division(), 
																			 new Operand( 1.0 )))), parser.parse( "1.5/  1" ));
	}
	
	@Test
	public void bracketsShouldBeParsed() throws ContentException { 
		assertEquals(new TokenCollection(new ArrayList<IToken>(Arrays.asList(new OpeningBracket(), 
																			 new Operand( 1.0 ), 
																			 new Addition(), 
																			 new Operand( 1.0 ), 
																			 new ClosingBracket()))), parser.parse( "(1.0+1.0)" ));
	}
	
	@Test
	public void negativeNumberShouldBeParsedToDouble() throws ContentException {
		assertEquals(new TokenCollection(new ArrayList<IToken>(Arrays.asList(new Operand( 1.0 ), 
																			 new Addition(), 
																			 new Operand( -1.0 )))), parser.parse( "1.0+-1.0" ));
	}
	
	@Test
	public void negativeNumberWithBracketsShouldBeParsedToDouble() throws ContentException {
		assertEquals(new TokenCollection(new ArrayList<IToken>(Arrays.asList(new Operand( 1.0 ), 
																			 new Addition(), 
																			 new OpeningBracket(), 
																			 new Operand( -1.0 ), 
																			 new ClosingBracket()))), parser.parse( "1.0+(-1.0)" ));
	}
	
	@Test(expected = InvalidOrderException.class)
	public void spaceInsideOperandsShouldInvokeException() throws ContentException {
		parser.parse( "1.3  5+0.1" );
	}
	
	@Test(expected = ContentException.class)
	public void illegalSymbolsShouldInvokeException() throws ContentException {
		parser.parse( "1.0@1.0+1.0" );
	}
	
	@Test(expected = ContentException.class)
	public void nonDigitSymbolInOperandShouldInvokeException() throws ContentException {
		parser.parse( "1.0+1.0+1*.0" );
	}
	
	@Test(expected = ContentException.class)
	public void operandWithTwoDecimalPointsShouldInvokeException() throws ContentException {
		parser.parse( "1.01.0+1.0" );
	}
	
	
	@Test(expected = ContentException.class)
	public void begginingInDecimalPointShouldInvokeException() throws ContentException {
		parser.parse( ".1+1" );
	}
	
	@Test(expected = ContentException.class)
	public void endingInDecimalPointShouldInvokeException() throws ContentException {
		parser.parse( "1+1." );
	}
}