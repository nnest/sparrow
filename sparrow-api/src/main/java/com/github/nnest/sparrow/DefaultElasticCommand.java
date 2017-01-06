/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * default elastic command
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class DefaultElasticCommand implements ElasticCommand {
	private ElasticCommandKind kind = null;
	private Object originalDocument = null;

	public DefaultElasticCommand() {
	}

	public DefaultElasticCommand(ElasticCommandKind kind, Object originalDocument) {
		this.setKind(kind);
		this.setOriginalDocument(originalDocument);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommand#getOriginalDocument()
	 */
	@Override
	public Object getOriginalDocument() {
		return originalDocument;
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
	 * @see com.github.nnest.sparrow.ElasticCommand#getKind()
	 */
	@Override
	public ElasticCommandKind getKind() {
		return kind;
	}

	/**
	 * @param kind
	 *            the kind to set
	 */
	public void setKind(ElasticCommandKind kind) {
		assert kind != null : "Elastic command kind cannot be null.";

		this.kind = kind;
	}
}
