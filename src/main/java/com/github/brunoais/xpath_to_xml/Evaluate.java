package com.github.brunoais.xpath_to_xml;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.jxpath.CompiledExpression;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.ri.compiler.Expression;
import org.apache.commons.jxpath.ri.compiler.LocationPath;
import org.apache.commons.jxpath.ri.compiler.NodeNameTest;
import org.apache.commons.jxpath.ri.compiler.NodeTest;
import org.apache.commons.jxpath.ri.compiler.Step;

public class Evaluate {

	static String fullBasePath = "";
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		CompiledExpression compiled = JXPathContext
				.compile("/PaintingJob/RoleList/PartyRole[RoleType/text()='me']/DistributorDetails/DistributorDetail[2]/Identifier");
		
		Method getExpression = compiled.getClass().getDeclaredMethod("getExpression");
		getExpression.setAccessible(true);
		LocationPath expression = (LocationPath) getExpression.invoke(compiled);
		
		for (Step step : expression.getSteps()) {
			NodeTest nodeTest = step.getNodeTest();
			if(nodeTest instanceof NodeNameTest){
				String name = ((NodeNameTest) nodeTest).getNodeName().getName();
				fullBasePath += "/" + name;
			}
		}
		
		System.out.println(fullBasePath);
	}
}

