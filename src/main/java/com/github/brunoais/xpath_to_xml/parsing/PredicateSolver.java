package com.github.brunoais.xpath_to_xml.parsing;

import java.util.ArrayList;

import org.apache.commons.jxpath.ri.compiler.CoreOperationAnd;
import org.apache.commons.jxpath.ri.compiler.CoreOperationEqual;
import org.apache.commons.jxpath.ri.compiler.Expression;

public class PredicateSolver {

	private ArrayList<String> currentPath;
	private Expression predicate;

	public PredicateSolver(ArrayList<String> currentPath, Expression predicate) {
		this.currentPath = currentPath;
		this.predicate = predicate;
		
	}

	public void resolvePredicate() {
		
		if(predicate instanceof CoreOperationAnd){
			Expression[] arguments = ((CoreOperationAnd) predicate).getArguments();
			for (Expression argument : arguments) {
				PredicateSolver pSolver = new PredicateSolver(new ArrayList<>(currentPath), argument);
				pSolver.resolvePredicate();
			}
		} else if(predicate instanceof CoreOperationEqual){
			Expression[] arguments = ((CoreOperationAnd) predicate).getArguments();
		}
		
	}

}
