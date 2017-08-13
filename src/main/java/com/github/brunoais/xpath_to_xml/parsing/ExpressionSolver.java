package com.github.brunoais.xpath_to_xml.parsing;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.jxpath.ri.compiler.CoreOperationAnd;
import org.apache.commons.jxpath.ri.compiler.CoreOperationEqual;
import org.apache.commons.jxpath.ri.compiler.Expression;
import org.apache.commons.jxpath.ri.compiler.LocationPath;
import org.apache.commons.jxpath.ri.compiler.Step;
import org.w3c.dom.Element;

public class ExpressionSolver {

	private DOMBuildingElement element;
	private Expression expression;

	public ExpressionSolver(DOMBuildingElement element, Expression expression) {
		this.element = element;
		this.expression = expression;
	}

	public void resolveExpression() {
		
		if(expression instanceof CoreOperationAnd){
			Expression[] arguments = ((CoreOperationAnd) expression).getArguments();
			for (Expression argument : arguments) {
				ExpressionSolver pSolver = new ExpressionSolver(element, argument);
				pSolver.resolveExpression();
			}
		} else if(expression instanceof CoreOperationEqual){
			Expression[] arguments = ((CoreOperationEqual) expression).getArguments();

			for (Expression argument : arguments) {
				ExpressionSolver pSolver = new ExpressionSolver(element, argument);
				pSolver.resolveExpression();
			}
		} else if(expression instanceof LocationPath){
			LocationPath location = (LocationPath) expression;
			Step[] steps = location.getSteps();
			for (Step step : steps) {
				StepSolver solver = new StepSolver(element, step);
				solver.solve();
			}
		}
		
	}

}
