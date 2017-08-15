package com.github.brunoais.xpath_to_xml.parsing;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.jxpath.ri.compiler.Constant;
import org.apache.commons.jxpath.ri.compiler.CoreOperationAnd;
import org.apache.commons.jxpath.ri.compiler.CoreOperationEqual;
import org.apache.commons.jxpath.ri.compiler.Expression;
import org.apache.commons.jxpath.ri.compiler.LocationPath;
import org.apache.commons.jxpath.ri.compiler.Step;
import org.w3c.dom.Element;

public class ExpressionSolver {

	private DOMBuildingElement element;
	private DOMBuildingElement child;
	private Expression expression;

	public ExpressionSolver(DOMBuildingElement element, Expression expression) {
		this.child = this.element = element;
		this.expression = expression;
	}

	public void resolveExpression() throws ParserConfigurationException {
		
		if(expression instanceof CoreOperationAnd){
			Expression[] arguments = ((CoreOperationAnd) expression).getArguments();
			for (Expression argument : arguments) {
				ExpressionSolver solver = new ExpressionSolver(element, argument);
				solver.resolveExpression();
				if(element == null){
					child = element = solver.child();
				} else {
					child = solver.child();
				}
			}
		} else if(expression instanceof CoreOperationEqual){
			Expression[] arguments = ((CoreOperationEqual) expression).getArguments();

			for (Expression argument : arguments) {
				ExpressionSolver solver = new ExpressionSolver(element, argument);
				solver.resolveExpression();
				if(element == null){
					child = element = solver.child();
				} else {
					child = solver.child();
				}
			}
		} else if(expression instanceof LocationPath){
			LocationPath location = (LocationPath) expression;
			Step[] steps = location.getSteps();
			for (Step step : steps) {
				StepSolver solver = new StepSolver(child, step);
				solver.solve();
				if(element == null){
					child = element = solver.child();
				} else {
					child = solver.child();
				}
			}

		} else if(expression instanceof Constant){
			
		}
		
		
	}

	public DOMBuildingElement root() {
		return element;
	}
	
	public DOMBuildingElement child() {
		return child;
	}

}
