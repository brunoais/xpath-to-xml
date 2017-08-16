package com.github.brunoais.xpath_to_xml.parsing;

import org.apache.commons.jxpath.ri.compiler.Constant;
import org.w3c.dom.Node;

public interface OverpassData {

	void handle(Node node);
	
	void handle(Constant constant);

}
