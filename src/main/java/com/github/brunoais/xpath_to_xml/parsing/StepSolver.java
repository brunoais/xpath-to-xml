package com.github.brunoais.xpath_to_xml.parsing;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.jxpath.ri.compiler.Expression;
import org.apache.commons.jxpath.ri.compiler.NodeNameTest;
import org.apache.commons.jxpath.ri.compiler.Step;
import org.w3c.dom.Element;

public class StepSolver {

	private DOMBuildingElement currentElement;
	private Step step;
	private DOMBuildingElement newChild;

	public StepSolver(DOMBuildingElement element, Step step) {
		this.currentElement = element;
		this.step = step;
		
	}
	
	public void solve() {

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
			
			newChild = currentElement.forceExistGetChildByTagName(name, 1, true);
			
		} else {
			System.out.println("Not node name: " + step);
		}
	}
	
	public DOMBuildingElement child() {
		return newChild;
	}
	
}
