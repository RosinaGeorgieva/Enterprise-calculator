package com.sap.internship.cli.itests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.sap.internship.cli.exception.CLICalculationException;
import com.sap.internship.cli.exception.CLIContentException;
import com.sap.internship.cli.pageobject.CalculatorPO;

public class AppIT {
	private CalculatorPO calculator;
	
	@Before
	public void createObject() {
		calculator = new CalculatorPO();
	}
	
	@Test
	public void shouldBeValidCase() throws Exception {
		assertEquals( calculator.execute( "(1.0-1.0)*1.0" ), 0.0, 0.0);
	}
	
	@Test(expected = CLIContentException.class)
	public void invalidInputShouldNotBeAllowed() throws Exception {
		calculator.execute( "a1" );
	}
	
	@Test(expected = CLICalculationException.class)
	public void invalidCalculationShouldNotBeAllowed() throws Exception {
		calculator.execute( "1.0/0" );
	}
}
