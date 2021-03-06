/**
 * 
 */
package com.github.nnest.sparrow.command.document;

import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticDocumentAnalyzer;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;

/**
 * index document, create it if not present.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Index implements ElasticCommand {
	private Object document = null;
	private ElasticDocumentDescriptor documentDescriptor = null;

	public Index(Object document) {
		assert document != null : "Document cannot be null.";

		this.document = document;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommand#getCommandKind()
	 */
	@Override
	public ElasticCommandKind getCommandKind() {
		return ElasticCommandKind.INDEX;
	}

	/**
	 * @param <T>
	 *            document
	 * @return the document
	 */
	@SuppressWarnings("unchecked")
	public <T> T getDocument() {
		return (T) document;
	}

	/**
	 * @param document
	 *            the document to set
	 */
	protected void setDocument(Object document) {
		this.document = document;
	}

	/**
	 * get document type
	 * 
	 * @return document type
	 */
	public Class<?> getDocumentType() {
		return this.getDocument().getClass();
	}

	/**
	 * @return the documentDescriptor
	 */
	public ElasticDocumentDescriptor getDocumentDescriptor() {
		return documentDescriptor;
	}

	/**
	 * @param documentDescriptor
	 *            the documentDescriptor to set
	 */
	protected void setDocumentDescriptor(ElasticDocumentDescriptor documentDescriptor) {
		this.documentDescriptor = documentDescriptor;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommand#analysis(com.github.nnest.sparrow.ElasticDocumentAnalyzer)
	 */
	@Override
	public ElasticCommand analysis(ElasticDocumentAnalyzer documentAnalyzer) {
		this.setDocumentDescriptor(documentAnalyzer.analysis(this.getDocumentType()));
		return this;
	}
}
