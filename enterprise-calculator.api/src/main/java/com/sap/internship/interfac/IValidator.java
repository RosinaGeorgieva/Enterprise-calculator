package com.sap.internship.interfac;

import com.sap.internship.exception.ContentException;
import com.sap.internship.pojo.TokenCollection;

public interface IValidator {
	public void validate(TokenCollection expression) throws ContentException;
}