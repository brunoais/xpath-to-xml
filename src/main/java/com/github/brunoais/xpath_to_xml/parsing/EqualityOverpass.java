package com.github.brunoais.xpath_to_xml.parsing;

import org.apache.commons.jxpath.ri.compiler.Constant;
import org.w3c.dom.Node;

import com.github.brunoais.xpath_to_xml.parsing.OverpassData.OverpassDataStatus;

public class EqualityOverpass implements OverpassData {

	Node node;
	Constant constant;
	OverpassDataStatus dataStatus = OverpassDataStatus.NONE;

	// prepare so two nodes are given
	
	@Override
	public void handle(Node node) {

		if(constant != null){
			assignConstantToNode(constant, node);
		} else if(this.node != null){
			assignNodeToNode(this.node, node);
		}
		this.node = node;
		gotValue();
	}

	private void assignNodeToNode(Node first, Node second) {
		
		if(second.getTextContent().trim().isEmpty()){
			// as expected, nice!
			second.setTextContent(first.getTextContent());
		} else if(first.getTextContent().trim().isEmpty()){
			// Alright.... the other way around....
			first.setTextContent(second.getTextContent());
		} else if(first.getTextContent().trim().equals(second.getTextContent().trim())){
			dataStatus = OverpassDataStatus.DONE_OK;
		} else {
			dataStatus = OverpassDataStatus.ALREADY_HAD_DIFFERENT_VALUE;
		}
		
	}

	@Override
	public void handle(Constant constant) {
		
		if(node != null){
			this.constant = constant;
			assignConstantToNode(constant, node);
		} else if(this.constant != null) {
			// TODO What if they are not equal?
			this.constant.compute(null).equals(constant.compute(null));
		}
		this.constant = constant;
		gotValue();
	}
	
	private void assignConstantToNode(Constant constant, Node node) {

		if(node.getTextContent().trim().length() == 0){
			node.setTextContent(constant.compute(null).toString());
			dataStatus = OverpassDataStatus.DONE_OK;
		} else if(node.getTextContent().trim().equals(constant.compute(null).toString())){
			dataStatus = OverpassDataStatus.DONE_OK;
		} else {
			dataStatus = OverpassDataStatus.ALREADY_HAD_DIFFERENT_VALUE;
		}
		
	}

	
	private void gotValue() {
		switch (dataStatus) {
			case NONE:
				dataStatus = OverpassDataStatus.FIRST_VALUE_SET;
				break;
			case FIRST_VALUE_SET:
				dataStatus = OverpassDataStatus.SECOND_VALUE_SET;
				break;
			default:/* NOOP */
		}
	}

	@Override
	public OverpassDataStatus status() {
		return dataStatus;
	}
}
