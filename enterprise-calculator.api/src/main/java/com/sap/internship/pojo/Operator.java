package com.sap.internship.pojo;

import com.sap.internship.interfac.IToken;

public abstract class Operator implements IToken {
	private char operator;
	
	public Operator(char operator) {
		super();
		this.operator = operator;
	}

	public char getChar() {
		return operator;
	}
	
	abstract public int getWeight();
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + operator;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operator other = (Operator) obj;
		if (operator != other.operator)
			return false;
		return true;
	}
}
