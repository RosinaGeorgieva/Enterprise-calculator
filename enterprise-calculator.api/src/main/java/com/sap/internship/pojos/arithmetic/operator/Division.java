package com.sap.internship.pojos.arithmetic.operator;

import com.sap.internship.exception.DivisionByZeroException;
import com.sap.internship.pojo.Operand;

public class Division extends ArithmeticOperator {
	public Division() {
		super('/');
	}
	
	@Override
	public Operand applyOn(Operand firstOperand, Operand secondOperand) throws DivisionByZeroException {
		if (secondOperand.getValue() == 0.0) {
			throw new DivisionByZeroException("Division by zero!");
		}
		return new Operand(firstOperand.getValue() / secondOperand.getValue());
	}

	@Override
	public int getWeight() {
		return 1;
	}
}
