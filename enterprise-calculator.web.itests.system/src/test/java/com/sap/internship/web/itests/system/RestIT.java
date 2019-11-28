package com.sap.internship.web.itests.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.sap.internship.web.calculator.dto.CalculationDTO;
import com.sap.internship.web.exception.BadRequestException;
import com.sap.internship.web.po.WebCalculatorPO;
import com.sap.internship.web.security.TokenFactory;
import com.sap.internship.web.service.Environment;

@TestMethodOrder(OrderAnnotation.class)
public class RestIT {
	private static final int CUSTOM_PORT = 8080;
	private static final String VERIFICATION_KEY = "key";
	private static final CalculationDTO VALID_CALCULATION = new CalculationDTO("1-1", new Double(0));
	private static final CalculationDTO INVALID_CALCULATION = new CalculationDTO("1$1", null);
	private static WebCalculatorPO calculator;

	@BeforeAll
	public static void setup() throws Exception {
		calculator = new WebCalculatorPO(new Environment(CUSTOM_PORT),	new TokenFactory(VERIFICATION_KEY).createSigned());
		Awaitility.await().atMost(30, TimeUnit.SECONDS).until(calculator::serviceIsAvailable);
	}

	@Test
	@Order(1)
	public void testValidCalculations() throws Exception {
		assertEquals((VALID_CALCULATION), calculator.evaluate(VALID_CALCULATION.getExpression()));
	}

	@Test
	@Order(2)
	public void testInvalidCalculations() throws Exception {
		assertThrows(BadRequestException.class, () -> {calculator.evaluate(INVALID_CALCULATION.getExpression());});
	}

	@Test
	@Order(3)
	public void testHistory() throws Exception {
		assertThat(calculator.getHistory(), CoreMatchers.hasItems(VALID_CALCULATION, INVALID_CALCULATION));
	}
}