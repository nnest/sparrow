/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * default elastic command result
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class DefaultElasticCommandResult implements ElasticCommandResult {
	private Object originalDocument = null;

	public DefaultElasticCommandResult() {

	}

	public DefaultElasticCommandResult(Object originalDocument) {
		this.setOriginalDocument(originalDocument);
	}

	/**
	 * @param originalDocument
	 *            the originalDocument to set
	 */
	public void setOriginalDocument(Object originalDocument) {
		this.originalDocument = originalDocument;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommandResult#getOriginalDocument()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getOriginalDocument() {
		return (T) this.originalDocument;
	}
}
