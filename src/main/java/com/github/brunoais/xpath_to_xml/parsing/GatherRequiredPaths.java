package com.github.brunoais.xpath_to_xml.parsing;

import java.util.ArrayList;

import org.apache.commons.jxpath.ri.compiler.Constant;
import org.apache.commons.jxpath.ri.compiler.CoreOperationEqual;
import org.apache.commons.jxpath.ri.compiler.Expression;
import org.apache.commons.jxpath.ri.compiler.LocationPath;
import org.apache.commons.jxpath.ri.compiler.NodeNameTest;
import org.apache.commons.jxpath.ri.compiler.Step;

public class GatherRequiredPaths {

	private ArrayList<String> paths;
	private ArrayList<String> currentPath;

	public GatherRequiredPaths() {
		paths = new ArrayList<String>();
		currentPath = new ArrayList<String>();
	}
	
	
	public void addPath(LocationPath path) {
		Step[] steps = path.getSteps();
		for (Step step : steps) {
			stepSolve(step);
		}
	}
	
	private void stepSolve(Step step) {
		
		StepSolver solver = new StepSolver(currentPath, step);
		

	}


	private void resolvePredicates(Expression[] predicates) {
		for (Expression predicate : predicates) {
			if (predicate instanceof CoreOperationEqual) {
				CoreOperationEqual equalPredicate = (CoreOperationEqual) predicate;
				for (Expression calculationArgument : equalPredicate.getArguments()) {
					if (calculationArgument instanceof LocationPath) {
						// Compiler.NODE_TYPE_TEXT
						// ^ text node
						pathMade2 = pathMade + calculationArgument.toString();
					} else if (predicate instanceof Constant) {

					}

			}
		}
	}
	
	
	
}
