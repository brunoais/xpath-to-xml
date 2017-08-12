package com.github.brunoais.xpath_to_xml.parsing;

import java.util.ArrayList;

import org.apache.commons.jxpath.ri.compiler.Expression;
import org.apache.commons.jxpath.ri.compiler.NodeNameTest;
import org.apache.commons.jxpath.ri.compiler.Step;

public class StepSolver {

	private ArrayList<String> currentPath;
	private Step step;

	public StepSolver(ArrayList<String> currentPath, Step step) {
		this.currentPath = new ArrayList<>(currentPath);
		this.step = step;
	}
	
	public void solve() {
		
		if(step.getNodeTest() instanceof NodeNameTest){

			Expression[] predicates = step.getPredicates();
			if (predicates.length > 0) {
				for (Expression predicate : predicates) {
					PredicateSolver pSolver = new PredicateSolver(new ArrayList<>(currentPath), predicate);
					pSolver.resolvePredicate();
				}
				
				
			}
		} else {
			System.out.println("Not node name: " + step);
		}
	}

}
