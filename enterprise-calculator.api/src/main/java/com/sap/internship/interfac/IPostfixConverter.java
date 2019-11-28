package com.sap.internship.interfac;

import com.sap.internship.exception.ContentException;
import com.sap.internship.pojo.TokenCollection;

public interface IPostfixConverter {
	public TokenCollection convert(TokenCollection expression) throws ContentException;
}
