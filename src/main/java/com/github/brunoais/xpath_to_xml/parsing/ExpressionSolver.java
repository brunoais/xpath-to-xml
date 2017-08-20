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
import org.w3c.dom.Node;

public class ExpressionSolver {

	private DOMBuildingElement element;
	private DOMBuildingElement child;
	private Expression expression;
	private EqualityOverpass messagePasser;

	public ExpressionSolver(DOMBuildingElement element, Expression expression, EqualityOverpass messagePasser) {
		this.child = this.element = element;
		this.expression = expression;
		this.messagePasser = messagePasser;
	}

	public ExpressionSolver(DOMBuildingElement root, Expression expression) {
		this(root, expression, null);
	}

	public void resolveExpression() {
		
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
			EqualityOverpass messagePasser = null;
			do{
				messagePasser = new EqualityOverpass();
				for (Expression argument : arguments) {
					ExpressionSolver solver = new ExpressionSolver(element, argument, messagePasser);
					solver.resolveExpression();
					if(element == null){
						child = element = solver.child();
					} else {
						child = solver.child();
					}
				}
				if(messagePasser.status() == OverpassData.OverpassDataStatus.ALREADY_HAD_DIFFERENT_VALUE){
					element.tryingAgain();
				}
			} while(messagePasser.status() == OverpassData.OverpassDataStatus.ALREADY_HAD_DIFFERENT_VALUE);
			
		} else if(expression instanceof LocationPath){
			LocationPath location = (LocationPath) expression;
			Step[] steps = location.getSteps();
			for (Step step : steps) {
				StepSolver solver = new StepSolver(child, step, messagePasser);
				solver.solve();
				if(element == null){
					child = element = solver.child();
				} else {
					child = solver.child();
				}
			}

		} else if(expression instanceof Constant){
			if(messagePasser != null){
				messagePasser.handle((Constant) expression);
			}
		}
		
		
	}

	public DOMBuildingElement root() {
		return element;
	}
	
	public DOMBuildingElement child() {
		return child;
	}

}
