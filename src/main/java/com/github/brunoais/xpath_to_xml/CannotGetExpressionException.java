package com.github.brunoais.xpath_to_xml;

import com.github.brunoais.xpath_to_xml.parsing.ParsingRuntimeException;

public class CannotGetExpressionException extends ParsingRuntimeException {

	protected CannotGetExpressionException(String message, Throwable cause) {
		super(message, cause);
	}

}
