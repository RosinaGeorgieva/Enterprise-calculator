package com.sap.internship.pojos.arithmetic.operator;

import com.sap.internship.pojo.Operand;

public class Addition extends ArithmeticOperator {
	public Addition() {
		super('+');
	}
	
	@Override
	public Operand applyOn(Operand firstOperand, Operand secondOperand) {
		return new Operand(firstOperand.getValue() + secondOperand.getValue());
	}

	@Override
	public int getWeight() {
		return 2;
	}
}
