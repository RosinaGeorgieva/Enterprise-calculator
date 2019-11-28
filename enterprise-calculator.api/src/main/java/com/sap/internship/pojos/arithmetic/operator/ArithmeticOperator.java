package com.sap.internship.pojos.arithmetic.operator;

import com.sap.internship.exception.DivisionByZeroException;
import com.sap.internship.pojo.Operand;
import com.sap.internship.pojo.Operator;

public abstract class ArithmeticOperator extends Operator{
	public ArithmeticOperator(char operator) {
		super(operator);
	}

	abstract public Operand applyOn(Operand firstOperand, Operand secondOperand) throws DivisionByZeroException;
}
