/**
 * 
 */
package com.github.nnest.sparrow.simple;

/**
 * document
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Document {
	private String index = null;
	private String type = null;
	private Object document = null;

	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the document
	 */
	public Object getDocument() {
		return document;
	}

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument(Object document) {
		this.document = document;
	}
}
