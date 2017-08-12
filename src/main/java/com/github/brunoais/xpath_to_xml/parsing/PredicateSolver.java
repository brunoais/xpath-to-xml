package com.github.brunoais.xpath_to_xml.parsing;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.jxpath.ri.compiler.CoreOperationAnd;
import org.apache.commons.jxpath.ri.compiler.CoreOperationEqual;
import org.apache.commons.jxpath.ri.compiler.Expression;

public class PredicateSolver {

	private ArrayList<String> currentPath;
	private Expression predicate;

	private ArrayList<String> extraPaths;

	public PredicateSolver(ArrayList<String> currentPath, Expression predicate) {
		this.currentPath = new ArrayList<>(currentPath);
		this.predicate = predicate;
		
		this.extraPaths = new ArrayList<>();
	}

	public void resolvePredicate() {
		
		if(predicate instanceof CoreOperationAnd){
			Expression[] arguments = ((CoreOperationAnd) predicate).getArguments();
			for (Expression argument : arguments) {
				PredicateSolver pSolver = new PredicateSolver(new ArrayList<>(currentPath), argument);
				pSolver.resolvePredicate();
				extraPaths.addAll(pSolver.pathsFound());
			}
		} else if(predicate instanceof CoreOperationEqual){
			Expression[] arguments = ((CoreOperationAnd) predicate).getArguments();
		}
		
	}

	public ArrayList<String> pathsFound() {
		return extraPaths;
	}

}
