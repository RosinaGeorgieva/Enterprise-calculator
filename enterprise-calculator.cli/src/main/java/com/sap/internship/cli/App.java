package com.sap.internship.cli;

import com.sap.internship.core.Parser;
import com.sap.internship.core.PostfixCalculator;
import com.sap.internship.core.PostfixConverter;
import com.sap.internship.core.Validator;
import com.sap.internship.exception.ContentException;
import com.sap.internship.exception.InvalidInputException;
import com.sap.internship.interfac.ICalculator;
import com.sap.internship.interfac.IParser;
import com.sap.internship.interfac.IPostfixConverter;
import com.sap.internship.interfac.IValidator;

public class App {
	public static void main(String[] args) throws InvalidInputException {
		IParser parser = new Parser();
		IValidator validator = new Validator();
		IPostfixConverter postfixConverter = new PostfixConverter();
		ICalculator calculator = new PostfixCalculator(parser, validator, postfixConverter);
		try {
			System.out.println(calculator.evaluate(args[0]));
		} catch (ContentException invalidOperation) {
			System.out.println("Expression content is invalid.");
		} catch (InvalidInputException divisionByZero) {
			System.out.println("Attempt for invalid calculation.");
		}
	}
}
