package com.sap.internship.web.service;

import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;
import com.sap.internship.web.po.WebCalculatorPO;
import com.sap.internship.web.security.VcapServices;

public class WebCalculatorLifecycle {
	private static File NULL_FILE = new File((System.getProperty("os.name").startsWith("Windows") ? "NUL" : "/dev/null"));
	private Process webCalculator;

	public WebCalculatorPO start(Environment environment, String token) throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.environment().put("CALCULATOR_PORT", String.valueOf(environment.getPort()));
		processBuilder.environment().put("VCAP_SERVICES", toString(environment.getVcapServices()));
		processBuilder.command("java", "-jar", constructPath());
		processBuilder.redirectOutput(NULL_FILE);
		processBuilder.redirectError(NULL_FILE);
		webCalculator = processBuilder.start();
		return new WebCalculatorPO(getURL(environment.getPort()), token);
	}

	public void destroy() {
		webCalculator.destroy();
	}

	public static String getURL(int port) {
		return new String("http://localhost:" + port);
	}

	private String toString(VcapServices vcapServices) {
		return new Gson().toJson(vcapServices).toString();
	}
	
	private String constructPath() {
		return String.format(".%starget%sdependencies%senterprise-calculator.web.release.jar", File.separator,
				File.separator, File.separator);
	}
}
