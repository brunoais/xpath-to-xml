package com.github.brunoais.xpath_to_xml.parsing;

import org.apache.commons.jxpath.ri.compiler.Constant;
import org.w3c.dom.Node;

public class EqualityOverpass implements OverpassData {

	Node node;
	Constant constant;

	// prepare so two nodes are given
	
	@Override
	public void handle(Node node) {
		
		if(constant != null){
			assignConstantToNode(constant, node);
		} else if(this.node != null){
			assignNodeToNode(this.node, node);
		}
		this.node = node;
	}
	
	private void assignNodeToNode(Node first, Node second) {
		
		if(second.getTextContent().trim().isEmpty()){
			// as expected, nice!
			second.setTextContent(first.getTextContent());
		} else if(first.getTextContent().trim().isEmpty()){
			// Alright.... the other way around....
			first.setTextContent(second.getTextContent());
		} else if(!first.getTextContent().trim().equals(second.getTextContent().trim())){
			// TODO they don't equal... 
		}
		// if they are equal, that's the expected, nice
		
	}

	@Override
	public void handle(Constant constant) {
		
		if(node != null){
			this.constant = constant;
			assignConstantToNode(constant, node);
		} else if(this.constant != null) {
			this.constant.compute(null).equals(constant.compute(null));
		}
		this.constant = constant;
	}
	
	private void assignConstantToNode(Constant constant, Node node) {

		Object constantValue = constant.compute(null);
		if(constantValue instanceof Number){
			node.setTextContent(constantValue.toString());
		} else if(constantValue instanceof String){
			node.setTextContent((String) constantValue);
		}
		
	}
	
}
