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

	private ElasticDocumentDescriptor descriptor = null;

	public DefaultElasticCommand() {
	}

	public DefaultElasticCommand(ElasticCommandKind kind, Object originalDocument,
			ElasticDocumentDescriptor descriptor) {
		this.setKind(kind);
		this.setOriginalDocument(originalDocument);
		this.setDescriptor(descriptor);
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

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommand#getDescriptor()
	 */
	@Override
	public ElasticDocumentDescriptor getDescriptor() {
		return descriptor;
	}

	/**
	 * @param descriptor
	 *            the descriptor to set
	 */
	public void setDescriptor(ElasticDocumentDescriptor descriptor) {
		assert descriptor != null : "Elastic document descriptor cannot be null.";
		
		this.descriptor = descriptor;
	}
}
