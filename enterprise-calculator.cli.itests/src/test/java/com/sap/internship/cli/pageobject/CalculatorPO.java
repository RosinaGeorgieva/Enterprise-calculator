package com.sap.internship.cli.pageobject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sap.internship.cli.exception.CLICalculationException;
import com.sap.internship.cli.exception.CLIContentException;
import com.sap.internship.cli.exception.CLIInvalidInputException;

public class CalculatorPO {
	public double execute(String expression) throws IOException, CLIInvalidInputException {
		return getOutput(startJar(constructPath(), expression));
	}
	
	private double getOutput(Process process) throws IOException, CLIInvalidInputException {
		BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String output = stdout.readLine();
		try {
			return Double.parseDouble(output);
		} catch (NumberFormatException exception) {
			if (output.equals("Expression content is invalid.")) {
				throw new CLIContentException("Expression content is invalid.");
			}
			throw new CLICalculationException("Attempt for invalid calculation.");
		}
	}

	private Process startJar(String path, String expression) throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("java", "-jar", path, expression);
		return processBuilder.start();

	}

	private String constructPath() {
		return String.format(".%starget%sdependencies%senterprise-calculator.cli-0.0.1.jar", File.separator,
				File.separator, File.separator);
	}
}
