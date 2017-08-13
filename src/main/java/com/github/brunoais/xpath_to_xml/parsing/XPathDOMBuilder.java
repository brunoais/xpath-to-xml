package com.github.brunoais.xpath_to_xml.parsing;

import java.util.ArrayList;

import org.apache.commons.jxpath.ri.compiler.Constant;
import org.apache.commons.jxpath.ri.compiler.CoreOperationEqual;
import org.apache.commons.jxpath.ri.compiler.Expression;
import org.apache.commons.jxpath.ri.compiler.LocationPath;
import org.apache.commons.jxpath.ri.compiler.Step;
import org.w3c.dom.Element;

public class XPathDOMBuilder {


	private DOMBuildingElement root;

	public XPathDOMBuilder(Element root) {
		this.root = DOMBuildingElement.fromElement(root);
		
	}
	
	public void execute(Expression[] predicates) {
		
		for (Expression predicate : predicates) {
			ExpressionSolver expSolver = new ExpressionSolver(root, predicate);
			expSolver.resolveExpression();
			
			if (predicate instanceof CoreOperationEqual) {
				CoreOperationEqual equalPredicate = (CoreOperationEqual) predicate;
				for (Expression calculationArgument : equalPredicate.getArguments()) {
					if (calculationArgument instanceof LocationPath) {
						// Compiler.NODE_TYPE_TEXT
						// ^ text node
						//pathMade2 = pathMade + calculationArgument.toString();
					} else if (predicate instanceof Constant) {

					}
				}
			}
		}
	}
	
	
	
}
