package com.sap.internship.web.itests.component;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.sap.internship.web.exception.UnauthenticatedUserException;
import com.sap.internship.web.po.WebCalculatorPO;
import com.sap.internship.web.security.TokenFactory;
import com.sap.internship.web.security.VcapServices;
import com.sap.internship.web.service.Environment;
import com.sap.internship.web.service.WebCalculatorLifecycle;

public class SecurityIT {
	private static final int CUSTOM_PORT = 8084;
	private static final VcapServices VCAP_SERVICES = new VcapServices();
	private static WebCalculatorLifecycle calculatorLifecycle;
	private static WebCalculatorPO calculator;

	@BeforeAll
	public static void setup() throws Exception {
		calculatorLifecycle = new WebCalculatorLifecycle();
		calculator = calculatorLifecycle.start(new Environment(CUSTOM_PORT, VCAP_SERVICES), new TokenFactory("invalid verification key").createSigned());
		Awaitility.await().atMost(30, TimeUnit.SECONDS).until(calculator::serviceIsAvailable);
	}

	@AfterAll
	public static void destroyObject() {
		calculatorLifecycle.destroy();
	}

	@Test
	public void testUnauthenticated() throws Exception {
		assertThrows(UnauthenticatedUserException.class, () -> { calculator.evaluate("1-1");} );
	}
}
