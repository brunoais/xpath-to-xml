package com.github.brunoais.xpath_to_xml.parsing;

import java.util.ArrayList;

import org.apache.commons.jxpath.ri.compiler.Expression;
import org.apache.commons.jxpath.ri.compiler.NodeNameTest;
import org.apache.commons.jxpath.ri.compiler.Step;

public class StepSolver {

	private ArrayList<String> currentPath;
	private Step step;

	private ArrayList<String> extraPaths;

	public StepSolver(ArrayList<String> currentPath, Step step) {
		this.currentPath = new ArrayList<>(currentPath);
		this.step = step;
		
		this.extraPaths = new ArrayList<>();
	}
	
	public void solve() {

		Expression[] predicates = step.getPredicates();
		if (predicates.length > 0) {
			for (Expression predicate : predicates) {
				PredicateSolver pSolver = new PredicateSolver(currentPath, predicate);
				pSolver.resolvePredicate();
				extraPaths.addAll(pSolver.pathsFound());
			}
		}
		
		if(step.getNodeTest() instanceof NodeNameTest){
			NodeNameTest nodeNaming = (NodeNameTest) step.getNodeTest();
			String name = nodeNaming.getNodeName().getName();
			
			currentPath.add(name);
			
		} else {
			System.out.println("Not node name: " + step);
		}
	}
	
	public ArrayList<String> newCurrentPath() {
		return currentPath;
	}

}
