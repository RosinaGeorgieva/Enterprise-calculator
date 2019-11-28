package com.sap.internship.web.security;

import org.json.JSONException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class TokenFactory {
	private static String key;
	private static final String iss = "authServer";
	private static final String name = "name";
	private static final String clientId = "client";
	private static final String zid = "zid";
	private static final int iat = 1572523188;
	private static final int exp = 1972523188;
	private static final String[] scope = new String[] {"create"};
	
	public TokenFactory(String key) {
		this.key = key;
	}
	
	public String createSigned() throws JSONException {
		Algorithm algorithm = Algorithm.HMAC256(key);
		return JWT.create().withIssuer(iss)
									.withClaim("name", name)
									.withClaim("client_id", clientId)
									.withClaim("zid", zid)
									.withClaim("iat", iat)
									.withClaim("exp", exp)
									.withArrayClaim("scope", scope).sign(algorithm);
	}
}
