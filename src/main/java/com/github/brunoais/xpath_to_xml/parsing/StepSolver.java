package com.github.brunoais.xpath_to_xml.parsing;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.jxpath.ri.Compiler;
import org.apache.commons.jxpath.ri.compiler.Constant;
import org.apache.commons.jxpath.ri.compiler.Expression;
import org.apache.commons.jxpath.ri.compiler.NodeNameTest;
import org.apache.commons.jxpath.ri.compiler.NodeTypeTest;
import org.apache.commons.jxpath.ri.compiler.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class StepSolver {
	static final Logger LOG = LoggerFactory.getLogger(StepSolver.class);

	private DOMBuildingElement currentElement;
	private Step step;
	private DOMBuildingElement newChild;
	private EqualityOverpass messagePasser;

	public StepSolver(DOMBuildingElement element, Step step, EqualityOverpass messagePasser) {
		this.currentElement = element;
		this.step = step;
		this.messagePasser = messagePasser;
		
	}
	
	public void solve() {

		Expression[] predicates = step.getPredicates();
		int nodePos = 1;
		
		// Special situation. Selecting an nth node
		if (predicates.length == 1 &&
				predicates[0] instanceof Constant &&
				predicates[0].compute(null) instanceof Number) {
			nodePos = ((Number) predicates[0].compute(null)).intValue();
		} else if (predicates.length > 0) {
			for (Expression predicate : predicates) {
				ExpressionSolver pSolver = new ExpressionSolver(currentElement, predicate, messagePasser);
				pSolver.resolveExpression();
			}
		}
		
		if(step.getNodeTest() instanceof NodeNameTest){
			NodeNameTest nodeNaming = (NodeNameTest) step.getNodeTest();
			String name = nodeNaming.getNodeName().getName();
			
			if(currentElement == null){
				newChild = documentWithChild(name);
			} else if(currentElement.getParentNode() instanceof Document && currentElement.getTagName().equals(name)){
				newChild = currentElement;
			} else {
				newChild = currentElement.forceExistGetChildByTagName(name, nodePos, true);				
			}

		} else if(step.getNodeTest() instanceof NodeTypeTest){
			NodeTypeTest nodetyping = (NodeTypeTest) step.getNodeTest();
			
			if(nodetyping.getNodeType() == Compiler.NODE_TYPE_TEXT){
				System.out.println("Expect text here: " + step);
				messagePasser.handle(currentElement);
			}
			
		} else {
			System.out.println("Not node name: " + step);
		}
	}
	
	private DOMBuildingElement documentWithChild(String name){
		Document doc;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element topElem = doc.createElement(name);
			doc.appendChild(topElem);
			return DOMBuildingElement.fromElement(topElem);
		} catch (ParserConfigurationException e) {
			// This just won't happen. Just registering to make sure
			LOG.error("Could not create document required to hold the nodes", e);
			throw new CannotMakeDocumentException("Could not create document required to hold the nodes", e);
		}
	}

	public DOMBuildingElement child() {
		return newChild;
	}
	
}
