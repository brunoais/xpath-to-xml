package com.github.brunoais.xpath_to_xml.parsing;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.jxpath.ri.compiler.CoreOperationAnd;
import org.apache.commons.jxpath.ri.compiler.CoreOperationEqual;
import org.apache.commons.jxpath.ri.compiler.Expression;
import org.apache.commons.jxpath.ri.compiler.LocationPath;
import org.apache.commons.jxpath.ri.compiler.Step;

public class ExpressionSolver {

	private ArrayList<String> currentPath;
	private Expression predicate;

	private ArrayList<String> extraPaths;

	public ExpressionSolver(ArrayList<String> currentPath, Expression predicate) {
		this.currentPath = new ArrayList<>(currentPath);
		this.predicate = predicate;
		
		this.extraPaths = new ArrayList<>();
	}

	public void resolvePredicate() {
		
		if(predicate instanceof CoreOperationAnd){
			Expression[] arguments = ((CoreOperationAnd) predicate).getArguments();
			for (Expression argument : arguments) {
				ExpressionSolver pSolver = new ExpressionSolver(new ArrayList<>(currentPath), argument);
				pSolver.resolvePredicate();
				extraPaths.addAll(pSolver.pathsFound());
			}
		} else if(predicate instanceof CoreOperationEqual){
			Expression[] arguments = ((CoreOperationEqual) predicate).getArguments();

			for (Expression argument : arguments) {
				ExpressionSolver pSolver = new ExpressionSolver(new ArrayList<>(currentPath), argument);
				pSolver.resolvePredicate();
				extraPaths.addAll(pSolver.pathsFound());
			}
		} else if(predicate instanceof LocationPath){
			LocationPath location = (LocationPath) predicate;
			Step[] steps = location.getSteps();
			for (Step step : steps) {
				StepSolver solver = new StepSolver(currentPath, step);
				solver.solve();
				extraPaths.addAll(solver.pathsFound());
			}
		}
		
	}

	public ArrayList<String> pathsFound() {
		return extraPaths;
	}

}
