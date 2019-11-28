package com.sap.internship.web.itests.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.web.WebAppConfiguration;

import com.sap.internship.web.calculator.dto.CalculationDTO;
import com.sap.internship.web.exception.BadRequestException;
import com.sap.internship.web.po.DatabasePO;
import com.sap.internship.web.po.WebCalculatorPO;
import com.sap.internship.web.security.TokenFactory;
import com.sap.internship.web.security.VcapServices;
import com.sap.internship.web.service.Environment;
import com.sap.internship.web.service.WebCalculatorLifecycle;

@SpringBootTest
@WebAppConfiguration
@ComponentScan("com.sap.internship.web.database")
public class RestIT {
	private static final int CUSTOM_PORT = 8084;
	private static final VcapServices VCAP_SERVICES = new VcapServices();
	private static WebCalculatorLifecycle calculatorLifecycle;
	private static WebCalculatorPO calculator;
	
	@Autowired
	private DatabasePO database;

	@BeforeAll
	public static void setup() throws Exception {
		calculatorLifecycle = new WebCalculatorLifecycle();
		calculator = calculatorLifecycle.start(new Environment(CUSTOM_PORT, VCAP_SERVICES), new TokenFactory(VCAP_SERVICES.getVerificationKey()).createSigned());
		Awaitility.await().atMost(30, TimeUnit.SECONDS).until(calculator::serviceIsAvailable);

	}

	@BeforeEach
	public void resetDatabase() {
		database.deleteAll();
	}

	@AfterAll
	public static void destroyCalculator() {
		calculatorLifecycle.destroy();
	}

	@Test
	public void testValidCalculations() throws Exception {
		CalculationDTO calculation = new CalculationDTO("1*1", new Double(1));
		assertEquals(calculation, calculator.evaluate("1*1"));	
		assertThat(database.getHistory(), CoreMatchers.hasItems(calculation));
	}

	@Test
	public void testInvalidCalculations() throws Exception {
		assertThrows(BadRequestException.class, () -> {calculator.evaluate("1$1");});
	}

	@Test
	public void testHistory() throws IOException {
		CalculationDTO calculation = database.insert(new CalculationDTO("1*1", new Double(1)));
		assertThat(calculator.getHistory(), CoreMatchers.hasItems(calculation));
	}
}