package com.sap.internship.interfac;

import com.sap.internship.exception.InvalidInputException;

public interface ICalculator {
	public double evaluate(String expression) throws InvalidInputException;
}