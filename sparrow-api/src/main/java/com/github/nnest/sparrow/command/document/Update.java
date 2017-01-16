/**
 * 
 */
package com.github.nnest.sparrow.command.document;

import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticDocumentAnalyzer;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;

/**
 * update document by given data.<br>
 * {@linkplain #detectNoopChanged} default is {@code false}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Update implements ElasticCommand {
	private boolean detectNoopChanged = false;
	private boolean asUpsert = false;
	private Object document = null;
	private ElasticDocumentDescriptor documentDescriptor = null;

	public Update(Object document) {
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
		return ElasticCommandKind.UPDATE;
	}

	/**
	 * @return the detectNoop
	 */
	public boolean isDetectNoopChanged() {
		return detectNoopChanged;
	}

	/**
	 * @param detectNoopChanged
	 *            the detectNoopChanged to set
	 * @return this
	 */
	public Update withDetectNoopChanged(boolean detectNoopChanged) {
		this.detectNoopChanged = detectNoopChanged;
		return this;
	}

	/**
	 * @return the asUpsert
	 */
	public boolean isAsUpsert() {
		return asUpsert;
	}

	/**
	 * @param asUpsert
	 *            the asUpsert to set
	 * @return this
	 */
	public Update withAsUpsert(boolean asUpsert) {
		this.asUpsert = asUpsert;
		return this;
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
