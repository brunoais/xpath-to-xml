package com.github.brunoais.xpath_to_xml.parsing;

import java.util.ArrayList;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

public class DOMBuildingElement extends DOMBuildingNode implements Element{

	private Element realElement;
	
	public enum TextResult{
		DONE,
		ALREADY_HAD_TEXT,
		ALREADY_HAS_ELEMENT_CHILD,
		;
	}


	public DOMBuildingElement(Element realElement, int inflexCount) {
		super(realElement, inflexCount);
		this.realElement = realElement;
	}
	
	public ArrayList<DOMBuildingElement> getChildrenByTagName(String name, boolean resetInflex){
		NodeList tagNameElements = getElementsByTagName(name);
		ArrayList<DOMBuildingElement> children = new ArrayList<>(tagNameElements.getLength());
		int newInflex = resetInflex ? 0 : inflexCount + 1;
		for (int i = 0; i < tagNameElements.getLength(); i++) {
			if(tagNameElements.item(i).getParentNode().isSameNode(realElement)){
				children.add(new DOMBuildingElement((Element) tagNameElements.item(i), newInflex));
			}
		}
		return children;
	}
	
	public DOMBuildingElement forceExistGetChildByTagName(String name, boolean resetInflex){
		return forceExistGetChildByTagName(name, 1, resetInflex);
	}
	
	/**
	 * @TODO Optimize by caching and by not executing as much code
	 */
	public DOMBuildingElement forceExistGetChildByTagName(String name, int index, boolean resetInflex){
		int newInflex = resetInflex ? 0 : inflexCount + 1;
		ArrayList<DOMBuildingElement> children = getChildrenByTagName(name, resetInflex);
		for(int i = children.size(); i <= index; i++){
			Element newNode = realElement.getOwnerDocument().createElement(name);
			realElement.appendChild(newNode);
			children.add(new DOMBuildingElement(newNode, newInflex));
		}
		return children.get(index - 1);
	}

	public TextResult forceTextOnEmptyText(String text) {
		
		NodeList childNodes = getChildNodes();
		
		for (int i = 0; i < childNodes.getLength(); i++) {
			if(childNodes.item(i).getNodeType() == Node.ELEMENT_NODE){
				return TextResult.ALREADY_HAS_ELEMENT_CHILD;
			}
		}
		
		Node firstChild = getFirstChild();
		
		// just leaving like that for now
		if(firstChild != null){
			if(firstChild.getNodeType() == Node.TEXT_NODE && firstChild.getTextContent().trim().length() > 0){
				return TextResult.ALREADY_HAD_TEXT;
			}
		}
		
		firstChild.setTextContent(text);
		return TextResult.DONE;
	}
	
	
	
	// Original methods kept intact
	
	public String getTagName() {
		return realElement.getTagName();
	}

	public String getAttribute(String name) {
		return realElement.getAttribute(name);
	}

	public void setAttribute(String name, String value) throws DOMException {
		realElement.setAttribute(name, value);
	}

	public void removeAttribute(String name) throws DOMException {
		realElement.removeAttribute(name);
	}

	public Attr getAttributeNode(String name) {
		return realElement.getAttributeNode(name);
	}

	public Attr setAttributeNode(Attr newAttr) throws DOMException {
		return realElement.setAttributeNode(newAttr);
	}

	public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
		return realElement.removeAttributeNode(oldAttr);
	}

	public String getNodeName() {
		return realElement.getNodeName();
	}

	public String getNodeValue() throws DOMException {
		return realElement.getNodeValue();
	}

	public NodeList getElementsByTagName(String name) {
		return realElement.getElementsByTagName(name);
	}

	public void setNodeValue(String nodeValue) throws DOMException {
		realElement.setNodeValue(nodeValue);
	}

	public String getAttributeNS(String namespaceURI, String localName) throws DOMException {
		return realElement.getAttributeNS(namespaceURI, localName);
	}

	public short getNodeType() {
		return realElement.getNodeType();
	}

	public Node getParentNode() {
		return realElement.getParentNode();
	}

	public NodeList getChildNodes() {
		return realElement.getChildNodes();
	}

	public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws DOMException {
		realElement.setAttributeNS(namespaceURI, qualifiedName, value);
	}

	public Node getFirstChild() {
		return realElement.getFirstChild();
	}

	public Node getLastChild() {
		return realElement.getLastChild();
	}

	public Node getPreviousSibling() {
		return realElement.getPreviousSibling();
	}

	public Node getNextSibling() {
		return realElement.getNextSibling();
	}

	public NamedNodeMap getAttributes() {
		return realElement.getAttributes();
	}

	public Document getOwnerDocument() {
		return realElement.getOwnerDocument();
	}

	public Node insertBefore(Node newChild, Node refChild) throws DOMException {
		return realElement.insertBefore(newChild, refChild);
	}

	public void removeAttributeNS(String namespaceURI, String localName) throws DOMException {
		realElement.removeAttributeNS(namespaceURI, localName);
	}

	public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
		return realElement.replaceChild(newChild, oldChild);
	}

	public Attr getAttributeNodeNS(String namespaceURI, String localName) throws DOMException {
		return realElement.getAttributeNodeNS(namespaceURI, localName);
	}

	public Node removeChild(Node oldChild) throws DOMException {
		return realElement.removeChild(oldChild);
	}

	public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
		return realElement.setAttributeNodeNS(newAttr);
	}

	public Node appendChild(Node newChild) throws DOMException {
		return realElement.appendChild(newChild);
	}

	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) throws DOMException {
		return realElement.getElementsByTagNameNS(namespaceURI, localName);
	}

	public boolean hasChildNodes() {
		return realElement.hasChildNodes();
	}

	public Node cloneNode(boolean deep) {
		return realElement.cloneNode(deep);
	}

	public boolean hasAttribute(String name) {
		return realElement.hasAttribute(name);
	}

	public boolean hasAttributeNS(String namespaceURI, String localName) throws DOMException {
		return realElement.hasAttributeNS(namespaceURI, localName);
	}

	public void normalize() {
		realElement.normalize();
	}

	public TypeInfo getSchemaTypeInfo() {
		return realElement.getSchemaTypeInfo();
	}

	public void setIdAttribute(String name, boolean isId) throws DOMException {
		realElement.setIdAttribute(name, isId);
	}

	public boolean isSupported(String feature, String version) {
		return realElement.isSupported(feature, version);
	}

	public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
		realElement.setIdAttributeNS(namespaceURI, localName, isId);
	}

	public String getNamespaceURI() {
		return realElement.getNamespaceURI();
	}

	public String getPrefix() {
		return realElement.getPrefix();
	}

	public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
		realElement.setIdAttributeNode(idAttr, isId);
	}

	public void setPrefix(String prefix) throws DOMException {
		realElement.setPrefix(prefix);
	}

	public String getLocalName() {
		return realElement.getLocalName();
	}

	public boolean hasAttributes() {
		return realElement.hasAttributes();
	}

	public String getBaseURI() {
		return realElement.getBaseURI();
	}

	public short compareDocumentPosition(Node other) throws DOMException {
		return realElement.compareDocumentPosition(other);
	}

	public String getTextContent() throws DOMException {
		return realElement.getTextContent();
	}

	public void setTextContent(String textContent) throws DOMException {
		realElement.setTextContent(textContent);
	}

	public boolean isSameNode(Node other) {
		return realElement.isSameNode(other);
	}

	public String lookupPrefix(String namespaceURI) {
		return realElement.lookupPrefix(namespaceURI);
	}

	public boolean isDefaultNamespace(String namespaceURI) {
		return realElement.isDefaultNamespace(namespaceURI);
	}

	public String lookupNamespaceURI(String prefix) {
		return realElement.lookupNamespaceURI(prefix);
	}

	public boolean isEqualNode(Node arg) {
		return realElement.isEqualNode(arg);
	}

	public Object getFeature(String feature, String version) {
		return realElement.getFeature(feature, version);
	}

	public Object setUserData(String key, Object data, UserDataHandler handler) {
		return realElement.setUserData(key, data, handler);
	}

	public Object getUserData(String key) {
		return realElement.getUserData(key);
	}

	public static DOMBuildingElement fromElement(Element element) {
		if(element instanceof DOMBuildingElement){
			return (DOMBuildingElement) element;
		} else {
			return new DOMBuildingElement(element, 0);
		}
	}
	
		
}
