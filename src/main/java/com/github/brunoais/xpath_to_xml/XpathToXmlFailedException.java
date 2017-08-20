package com.github.brunoais.xpath_to_xml;

/**
 * Something went wrong while parsing. Check the cause for the reason why
 */
public class XpathToXmlFailedException extends Exception {
	private static final long serialVersionUID = 1L;

	protected XpathToXmlFailedException(Throwable cause) {
		super(cause);
	}

}
