package com.github.brunoais.xpath_to_xml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.jxpath.CompiledExpression;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.ri.compiler.Expression;
import org.w3c.dom.Document;

import com.github.brunoais.xpath_to_xml.parsing.XPathDOMBuilder;

public class FirstTest {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParserConfigurationException, IOException, TransformerException {
		CompiledExpression compiled = JXPathContext
				.compile("/PaintingJob/RoleList/PartyRole[RoleType/du/text()='me']/DistributorDetails/DistributorDetail[2]/Identifier");
		
		Method getExpression = compiled.getClass().getDeclaredMethod("getExpression");
		getExpression.setAccessible(true);
		Expression expression = (Expression) getExpression.invoke(compiled);
		
		
		XPathDOMBuilder builder = new XPathDOMBuilder();
		builder.execute(expression);
		
		Document doc = builder.generatedDocument();
		
		printDocument(doc, System.out);
		
		if(1 == 1){
			expression.hashCode();
			
		}
		
	}
	
	public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	    transformer.transform(new DOMSource(doc), 
	         new StreamResult(new OutputStreamWriter(out, "UTF-8")));
	}
}
