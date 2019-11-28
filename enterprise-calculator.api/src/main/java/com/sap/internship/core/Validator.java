package com.sap.internship.core;

import java.util.List;

import com.sap.internship.exception.ContentException;
import com.sap.internship.exception.InvalidOrderException;
import com.sap.internship.interfac.IToken;
import com.sap.internship.interfac.IValidator;
import com.sap.internship.pojo.Operator;
import com.sap.internship.pojo.TokenCollection;
import com.sap.internship.pojos.arithmetic.operator.ArithmeticOperator;
import com.sap.internship.pojos.arithmetic.operator.Subtraction;
import com.sap.internship.pojos.bracket.ClosingBracket;
import com.sap.internship.pojos.bracket.OpeningBracket;

public class Validator implements IValidator {
	public void validate(TokenCollection tokens) throws ContentException {
		if(!orderIsValid(tokens)) {
			throw new InvalidOrderException("InvalidOrderException");
		}
	}

	private boolean orderIsValid(TokenCollection tokens) {
		return !hasInvalidEnds(tokens) && !hasInvalidMiddle(tokens);
	}
	
    private boolean hasInvalidEnds(TokenCollection expression) {
    	return startsWithOperator(expression) || endsWithOperator(expression);
    }
    
    private boolean startsWithOperator(TokenCollection expression) {
    	return expression.getTokens().get(0) instanceof ArithmeticOperator && !(expression.getTokens().get(0) instanceof Subtraction);
    }
    
    private boolean endsWithOperator(TokenCollection expression) {
    	return expression.getTokens().get(expression.getTokens().size() - 1) instanceof Operator && !(expression.getTokens().get(expression.getTokens().size() - 1) instanceof ClosingBracket);
    }
	
    private boolean hasInvalidMiddle(TokenCollection expression) {
    	int brackets = 0;
    	List<IToken> tokens = expression.getTokens();
    	for(int index = 0; index < tokens.size() - 1; index++) {
    		if (hasInvalidSequentialOperators(tokens.get(index), tokens.get(index+1))) {
    			return true;
    		} else if(tokens.get(index) instanceof OpeningBracket) {
        		brackets++;
        	} else if (tokens.get(index) instanceof ClosingBracket) {
        		brackets--;
        	}
        }
        return brackets != 0;
    }
    
    private boolean hasInvalidSequentialOperators(IToken current, IToken next) {
    	return hasSequentialOperators(current, next) && !isOpeningBracketBeforeNumber(current, next) && !isClosingBracketAfterNumber(current, next);
    }
    
    private boolean hasSequentialOperators(IToken current, IToken next) {
		return current instanceof Operator && next instanceof ArithmeticOperator;
	}
    
	private boolean isOpeningBracketBeforeNumber(IToken current, IToken next) {
		return current instanceof ArithmeticOperator && next instanceof OpeningBracket;
	}
	
	private boolean isClosingBracketAfterNumber(IToken current, IToken next) {
		return current instanceof ClosingBracket && next instanceof ArithmeticOperator;
	}
}
