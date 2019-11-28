package com.sap.internship.web.exception;

public class UnauthenticatedUserException extends Exception {
	public UnauthenticatedUserException(String message) {
		super(message);
	}
}