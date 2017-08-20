package com.github.brunoais.xpath_to_xml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class FirstTest {
	public static void main(String[] args) throws Throwable {

		XPathDOMBuilder builder = new XPathDOMBuilder();
		builder.execute("/PaintingJob/RoleList/PartyRole[RoleType/du/text()='me']/DistributorDetails/DistributorDetail[1]/Identifier");
		builder.execute("/PaintingJob/RoleList/PartyRole[RoleType/du/text()='me']/DistributorDetails/DistributorDetail[3]/Identifier");
		builder.execute("/PaintingJob/RoleList/PartyRole[RoleType/du/text()='you']/DistributorDetails/DistributorDetail[2]/Identifier");
		
		Document doc = builder.generatedDocument();
		
		printDocument(doc, System.out);
		
		// for breakpoint purposes
		doc.hashCode();
		
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
