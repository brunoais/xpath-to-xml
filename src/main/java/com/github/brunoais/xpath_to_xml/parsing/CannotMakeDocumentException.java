package com.github.brunoais.xpath_to_xml.parsing;

/**
 * For some reason, could not create the document required to build the XMLDOM
 *
 */
public class CannotMakeDocumentException extends ParsingRuntimeException {
	private static final long serialVersionUID = 1L;

	protected CannotMakeDocumentException(String message, Throwable cause) {
		super(message, cause);
	}

}
