package com.github.brunoais.xpath_to_xml.parsing;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.jxpath.CompiledExpression;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.ri.compiler.Constant;
import org.apache.commons.jxpath.ri.compiler.CoreOperationEqual;
import org.apache.commons.jxpath.ri.compiler.Expression;
import org.apache.commons.jxpath.ri.compiler.LocationPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XPathDOMBuilder {
	public static final Logger LOG = LoggerFactory.getLogger(XPathDOMBuilder.class);

	private DOMBuildingElement root;
	private Document myDocument;
	
	public XPathDOMBuilder(Document doc) throws ParserConfigurationException {
		
		myDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		
		Node firstChild = doc.getFirstChild();
		if(firstChild instanceof Element){
			this.root = DOMBuildingElement.fromElement((Element) firstChild);
			return;
		}
		if(firstChild == null){
			doc.createElement("root");
		}
		
		
		this.root = DOMBuildingElement.fromElement(root);
		
	}
	public XPathDOMBuilder(Element root) {
		this.root = DOMBuildingElement.fromElement(root);
		
	}
	
	public XPathDOMBuilder() {
		root = null;
	}

	public void execute(String xQuery) throws ParserConfigurationException {
		CompiledExpression compiled = JXPathContext
				.compile("/PaintingJob/RoleList/PartyRole[RoleType/text()='me']/DistributorDetails/DistributorDetail[1]/Identifier");
		try{
			Method getExpression = compiled.getClass().getDeclaredMethod("getExpression");
			getExpression.setAccessible(true);
			Expression expression = (Expression) getExpression.invoke(compiled);
			execute(expression);
		}catch (RuntimeException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			LOG.error("", e);
		}
	}
	
//	public void execute(Expression[] expressions) throws ParserConfigurationException {
	
//			
//			if (predicate instanceof CoreOperationEqual) {
//				CoreOperationEqual equalPredicate = (CoreOperationEqual) predicate;
//				for (Expression calculationArgument : equalPredicate.getArguments()) {
//					if (calculationArgument instanceof LocationPath) {
//						// Compiler.NODE_TYPE_TEXT
//						// ^ text node
//						//pathMade2 = pathMade + calculationArgument.toString();
//					} else if (predicate instanceof Constant) {
//
//					}
//				}
//			}
//		}
//	}
	
	public void execute(Expression expression) throws ParserConfigurationException {
		ExpressionSolver expSolver = new ExpressionSolver(root, expression);
		expSolver.resolveExpression();
		root = expSolver.root();
	}
	public Document generatedDocument() {
		return root.getOwnerDocument();
	}
	
	
	
}
