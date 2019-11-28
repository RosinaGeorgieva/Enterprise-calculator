package com.sap.internship.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class WebSecurityUtil {
	
	private static Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
	
	public static String currentUser() {
		return auth.getName();
	}
}
