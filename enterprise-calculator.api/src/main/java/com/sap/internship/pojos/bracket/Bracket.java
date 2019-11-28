package com.sap.internship.pojos.bracket;

import com.sap.internship.pojo.Operator;

public abstract class Bracket extends Operator {
	public Bracket(char operator) {
		super(operator);
	}

	@Override
	public int getWeight() {
		return 0;
	}
}
