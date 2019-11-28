package com.sap.internship.web.service;

import com.sap.internship.web.security.VcapServices;

public class Environment {		
	private int port;
	private VcapServices vcapServices;
	
	public Environment(int port) {
		this.port = port;
	}

	public Environment(int port, VcapServices vcapServices) {
		this(port);
		this.vcapServices = vcapServices;
	}

	public int getPort() {
		return port;
	}

	public  VcapServices getVcapServices() {
		return vcapServices;
		
	}
}