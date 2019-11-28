package com.sap.internship.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import com.sap.internship.exception.ContentException;
import com.sap.internship.exception.InvalidTokenException;
import com.sap.internship.interfac.IPostfixConverter;
import com.sap.internship.interfac.IToken;
import com.sap.internship.pojo.Operand;
import com.sap.internship.pojo.Operator;
import com.sap.internship.pojo.OperatorProvider;
import com.sap.internship.pojo.TokenCollection;
import com.sap.internship.pojos.bracket.ClosingBracket;
import com.sap.internship.pojos.bracket.OpeningBracket;

public class PostfixConverter implements IPostfixConverter {

	public TokenCollection convert(TokenCollection expression) throws ContentException {
		return infixToPostfix(expression);
	}

	private TokenCollection infixToPostfix(TokenCollection expression) throws InvalidTokenException {
		Stack<Operator> stack = new Stack<>();
		List<IToken> tokens = new ArrayList<IToken>(expression.getTokens());
		List<IToken> tokensPostfix = new ArrayList<IToken>();
		for (int i = 0; i < tokens.size(); ++i) {
			IToken token = tokens.get(i);
			if (token instanceof Operand) {
				addOperandToResult(token, tokensPostfix);
			} else if (token instanceof OpeningBracket) {
				addToStack(token, stack);
			} else if (token instanceof ClosingBracket) {
				getExpressionFromBrackets(stack, tokensPostfix);
			} else {
				applyPrecedence(stack, tokensPostfix, token);
			}
		}
		extract(stack, tokensPostfix);
		return new TokenCollection(tokensPostfix);
	}

	private void addOperandToResult(IToken token, List<IToken> tokensPostfix) {
		tokensPostfix.add(new Operand(((Operand) token).getValue()));
	}

	private void addToStack(IToken token, Stack<Operator> stack) {
		stack.push((Operator) token);
	}

	private void getExpressionFromBrackets(Stack<Operator> stack, List<IToken> tokensPostfix) throws InvalidTokenException {
		while (!openingBracketIsNotFound(stack)) {
			addOperatorToResult(stack, tokensPostfix);
		}
		checkForRemainingBrackets(stack);
	}

	private boolean openingBracketIsNotFound(Stack<Operator> stack) {
		return stack.isEmpty() || ((Operator) stack.peek()).getChar() == '(';
	}

	private void addOperatorToResult(Stack<Operator> stack, List<IToken> tokensPostfix) throws InvalidTokenException {
		tokensPostfix.add(OperatorProvider.getObject(topOperator(stack)));
	}

	private void checkForRemainingBrackets(Stack<Operator> stack) {
		if (!stack.isEmpty()) {
			stack.pop();
		}
	}

	private void applyPrecedence(Stack<Operator> stack, List<IToken> tokensPostfix, IToken token) throws InvalidTokenException {
		while (hasHigherPriorityOperator(stack, token)) {
			if (!lastIsOpeningBracket(stack)) {
				addOperatorToResult(stack, tokensPostfix);
			} else {
				stack.pop();
			}
		}
		addToStack(token, stack);
	}

	private boolean lastIsOpeningBracket(Stack<Operator> stack) {
		return ((Operator) stack.peek()).getChar() == '(';
	}

	private boolean hasHigherPriorityOperator(Stack<Operator> stack, IToken token) {
		return !stack.empty() && ((Operator) token).getWeight() >= ((Operator) stack.peek()).getWeight();
	}

	private char topOperator(Stack<Operator> stack) {
		return ((Operator) stack.pop()).getChar();
	}

	private void extract(Stack<Operator> stack, List<IToken> tokensPostfix) throws InvalidTokenException {
		while (!stack.isEmpty()) {
			addOperatorToResult(stack, tokensPostfix);
		}
	}
}