package com.github.brunoais.xpath_to_xml.parsing;

import org.apache.commons.jxpath.ri.compiler.Constant;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class EqualityOverpass implements OverpassData {

	Node node;
	Constant constant;
	
	
	public void handleNode(Node node) {
		this.node = node;
		if(constant != null){
			assignConstantToNode();
		}
	}
	
	public void handleConstant(Constant constant) {
		this.constant = constant;
		if(node != null){
			assignConstantToNode();
		}
	}
	
	private void assignConstantToNode() {

		Object constantValue = constant.compute(null);
		if(constantValue instanceof Number){
			node.setTextContent(constantValue.toString());
		} else if(constantValue instanceof String){
			node.setTextContent((String) constantValue);
		}
		
	}
	
}
