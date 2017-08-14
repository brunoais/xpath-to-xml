package com.github.brunoais.xpath_to_xml.parsing;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.jxpath.ri.compiler.Expression;
import org.apache.commons.jxpath.ri.compiler.NodeNameTest;
import org.apache.commons.jxpath.ri.compiler.Step;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class StepSolver {

	private DOMBuildingElement currentElement;
	private Step step;
	private DOMBuildingElement newChild;

	public StepSolver(DOMBuildingElement element, Step step) {
		this.currentElement = element;
		this.step = step;
		
	}
	
	public void solve() throws ParserConfigurationException {

		Expression[] predicates = step.getPredicates();
		if (predicates.length > 0) {
			for (Expression predicate : predicates) {
				ExpressionSolver pSolver = new ExpressionSolver(currentElement, predicate);
				pSolver.resolveExpression();
			}
		}
		
		if(step.getNodeTest() instanceof NodeNameTest){
			NodeNameTest nodeNaming = (NodeNameTest) step.getNodeTest();
			String name = nodeNaming.getNodeName().getName();
			
			if(currentElement == null){
				newChild = documentWithChild(name);
			} else {
				newChild = currentElement.forceExistGetChildByTagName(name, 1, true);				
			}
			
		} else {
			System.out.println("Not node name: " + step);
		}
	}
	
	private DOMBuildingElement documentWithChild(String name) throws ParserConfigurationException {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element topElem = doc.createElement(name);
		doc.appendChild(topElem);
		return DOMBuildingElement.fromElement(topElem);
	}

	public DOMBuildingElement child() {
		return newChild;
	}
	
}
