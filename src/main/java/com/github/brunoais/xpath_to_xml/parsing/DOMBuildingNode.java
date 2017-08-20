package com.github.brunoais.xpath_to_xml.parsing;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

public class DOMBuildingNode implements Node{
	static final Logger LOG = LoggerFactory.getLogger(DOMBuildingNode.class);

	Node realNode;
	/*
	 * The inflex is used to know how far up in the tree to go in order to create a clone with a tag with a new value
	 * so that equals tests pass
	 * In short, for the XPath syntax, inflex resets on each "[" and adds on each "/"
	 */
	int inflexCount;

	public DOMBuildingNode(Node realNode, int inflexCount) {
		this.realNode = realNode;
		this.inflexCount = inflexCount;
	}
	
	
	
	
	// Node methods kept intact

	public String getNodeName() {
		return realNode.getNodeName();
	}

	public String getNodeValue() throws DOMException {
		return realNode.getNodeValue();
	}

	public void setNodeValue(String nodeValue) throws DOMException {
		realNode.setNodeValue(nodeValue);
	}

	public short getNodeType() {
		return realNode.getNodeType();
	}

	public Node getParentNode() {
		return realNode.getParentNode();
	}

	public NodeList getChildNodes() {
		return realNode.getChildNodes();
	}

	public Node getFirstChild() {
		return realNode.getFirstChild();
	}

	public Node getLastChild() {
		return realNode.getLastChild();
	}

	public Node getPreviousSibling() {
		return realNode.getPreviousSibling();
	}

	public Node getNextSibling() {
		return realNode.getNextSibling();
	}

	public NamedNodeMap getAttributes() {
		return realNode.getAttributes();
	}

	public Document getOwnerDocument() {
		return realNode.getOwnerDocument();
	}

	public Node insertBefore(Node newChild, Node refChild) throws DOMException {
		return realNode.insertBefore(newChild, refChild);
	}

	public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
		return realNode.replaceChild(newChild, oldChild);
	}

	public Node removeChild(Node oldChild) throws DOMException {
		return realNode.removeChild(oldChild);
	}

	public Node appendChild(Node newChild) throws DOMException {
		return realNode.appendChild(newChild);
	}

	public boolean hasChildNodes() {
		return realNode.hasChildNodes();
	}

	public Node cloneNode(boolean deep) {
		return realNode.cloneNode(deep);
	}

	public void normalize() {
		realNode.normalize();
	}

	public boolean isSupported(String feature, String version) {
		return realNode.isSupported(feature, version);
	}

	public String getNamespaceURI() {
		return realNode.getNamespaceURI();
	}

	public String getPrefix() {
		return realNode.getPrefix();
	}

	public void setPrefix(String prefix) throws DOMException {
		realNode.setPrefix(prefix);
	}

	public String getLocalName() {
		return realNode.getLocalName();
	}

	public boolean hasAttributes() {
		return realNode.hasAttributes();
	}

	public String getBaseURI() {
		return realNode.getBaseURI();
	}

	public short compareDocumentPosition(Node other) throws DOMException {
		return realNode.compareDocumentPosition(other);
	}

	public String getTextContent() throws DOMException {
		return realNode.getTextContent();
	}

	public void setTextContent(String textContent) throws DOMException {
		realNode.setTextContent(textContent);
	}

	public boolean isSameNode(Node other) {
		return realNode.isSameNode(other);
	}

	public String lookupPrefix(String namespaceURI) {
		return realNode.lookupPrefix(namespaceURI);
	}

	public boolean isDefaultNamespace(String namespaceURI) {
		return realNode.isDefaultNamespace(namespaceURI);
	}

	public String lookupNamespaceURI(String prefix) {
		return realNode.lookupNamespaceURI(prefix);
	}

	public boolean isEqualNode(Node arg) {
		return realNode.isEqualNode(arg);
	}

	public Object getFeature(String feature, String version) {
		return realNode.getFeature(feature, version);
	}

	public Object setUserData(String key, Object data, UserDataHandler handler) {
		return realNode.setUserData(key, data, handler);
	}

	public Object getUserData(String key) {
		return realNode.getUserData(key);
	}
	
	@Override
	public String toString() {
		// Arbitrary starting size because it is useful
		StringWriter outin = new StringWriter(1300);
	    try {		
		    TransformerFactory tf = TransformerFactory.newInstance();
		    Transformer transformer = tf.newTransformer();
		    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(new DOMSource(realNode), 
			     new StreamResult(outin));
			return outin.toString();
		} catch (TransformerException e) {
			LOG.warn("Could not transform Node into readable output", e);
			return realNode.toString();
		}
	}
	
}
