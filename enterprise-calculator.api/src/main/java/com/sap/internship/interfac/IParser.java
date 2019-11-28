package com.sap.internship.interfac;

import com.sap.internship.exception.ContentException;
import com.sap.internship.pojo.TokenCollection;

public interface IParser {
	public TokenCollection parse(String expression) throws ContentException;
}