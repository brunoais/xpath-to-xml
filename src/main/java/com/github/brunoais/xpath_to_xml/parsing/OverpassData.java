package com.github.brunoais.xpath_to_xml.parsing;

import org.apache.commons.jxpath.ri.compiler.Constant;
import org.w3c.dom.Node;

import com.github.brunoais.xpath_to_xml.parsing.OverpassData.OverpassDataStatus;

public interface OverpassData {

	
	public enum OverpassDataStatus {
		NONE,
		FIRST_VALUE_SET,
		SECOND_VALUE_SET,
		DONE_OK,
		ALREADY_HAD_DIFFERENT_VALUE,
		
	}

	
	
	void handle(Node node);
	
	void handle(Constant constant);
	
	OverpassDataStatus status();

}
