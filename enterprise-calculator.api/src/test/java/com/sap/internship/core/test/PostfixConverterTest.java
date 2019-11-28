package com.sap.internship.core.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sap.internship.core.PostfixConverter;
import com.sap.internship.exception.ContentException;
import com.sap.internship.exception.InvalidInputException;
import com.sap.internship.interfac.IParser;
import com.sap.internship.interfac.IToken;
import com.sap.internship.pojo.Operand;
import com.sap.internship.pojo.TokenCollection;
import com.sap.internship.pojos.arithmetic.operator.Addition;
import com.sap.internship.pojos.arithmetic.operator.Multiplication;
import com.sap.internship.pojos.bracket.ClosingBracket;
import com.sap.internship.pojos.bracket.OpeningBracket;

public class PostfixConverterTest {
	@Mock
	private IParser parser;
	
	@InjectMocks
	private PostfixConverter converter;

	@Before
	public void createObject() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void operatorPrecedenceShouldBeConsidered() throws InvalidInputException {
		ArrayList<IToken> infixContent = new ArrayList<IToken>(Arrays.asList(new Operand( 1.0 ), 
																			  new Addition(), 
																			  new Operand( 2.0 ), 
																			  new Multiplication(), 
																			  new Operand( 1.5 ), 
																			  new Addition(), 
																			  new Operand( 3.0 )));
		ArrayList<IToken> postfixContent = new ArrayList<IToken>(Arrays.asList(new Operand( 1.0 ), 
																			   new Operand( 2.0 ), 
																			   new Operand( 1.5 ), 
																			   new Multiplication(), 
																			   new Addition(), 
																			   new Operand( 3.0 ),  
																			   new Addition()));
		assertTokenSerializeEquals(infixContent, postfixContent);
	}
	
	@Test
	public void bracketsShouldBeConsidered() throws InvalidInputException {
		ArrayList<IToken> infixContent = new ArrayList<IToken>(Arrays.asList(new OpeningBracket(), 
																			 new Operand( 1.0 ), 
																			 new Addition(), 
																			 new Operand( 1.0 ), 
																			 new ClosingBracket(), 
																			 new Multiplication(), 
																			 new Operand( -1.0 )));
		ArrayList<IToken> postfixContent = new ArrayList<IToken>(Arrays.asList(new Operand( 1.0 ), 
																			   new Operand( 1.0 ), 
																			   new Addition(), 
																			   new Operand( -1 ),  
																			   new Multiplication()));
		assertTokenSerializeEquals(infixContent, postfixContent);
	}
	
	private void assertTokenSerializeEquals(ArrayList<IToken> infixContent, ArrayList<IToken> postfixContent) throws ContentException {
		TokenCollection infix = new TokenCollection(infixContent);
		TokenCollection postfix = new TokenCollection(postfixContent);
		assertEquals(postfix, converter.convert(infix));
	}
}
