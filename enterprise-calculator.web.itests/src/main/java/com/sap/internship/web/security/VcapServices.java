package com.sap.internship.web.security;

public class VcapServices {
	private XsuaaProperties[] xsuaa;
	
	public VcapServices() {
		xsuaa = new XsuaaProperties[1];
		xsuaa[0] = new XsuaaProperties();
	}
	
	private static class XsuaaProperties {
		private String[] tags;
		private Credentials credentials;
		
		private XsuaaProperties() {
			tags = new String[1];
			tags[0] = "xsuaa";
			credentials = new Credentials();
		}
		
		public Credentials getCredentials() {
			return credentials;
		}
	}

	private static class Credentials {
		private String clientid = "client";
		private String clientsecret = "secret";
		private String url = "url";
		private String verificationkey = "key";
		private String identityzone = "zone";
		private String xsappname = "appname";
		
		public String getVerificationKey() {
			return verificationkey;
		}

		public String getClientId() {
			return clientid;
		}
	}
	
	public String getVerificationKey() {
		return xsuaa[0].getCredentials().getVerificationKey();
	}
	
	public String getClientId() {
		return xsuaa[0].getCredentials().getClientId();
	}

	public XsuaaProperties[] getXsuaa() {
		return xsuaa;
	}
}
