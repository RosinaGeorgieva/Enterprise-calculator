package com.sap.internship.pojo;

import com.sap.internship.interfac.IToken;

public class Operand implements IToken{
	private double operand;
	
	public Operand(double operand) {
		this.operand = operand;
	}

	public double getValue() {
		return operand;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(operand);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Operand other = (Operand) obj;
		if (Double.doubleToLongBits(operand) != Double.doubleToLongBits(other.operand))
			return false;
		return true;
	}
}
