package com.github.brunoais.xpath_to_xml.parsing;

/**
 * This exception is a "catch all" for all exceptions within DOM building
 *
 */
public abstract class ParsingRuntimeException extends RuntimeException {

	protected ParsingRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	protected ParsingRuntimeException(String message) {
		super(message);
	}

	protected ParsingRuntimeException(Throwable cause) {
		super(cause);
	}

}
