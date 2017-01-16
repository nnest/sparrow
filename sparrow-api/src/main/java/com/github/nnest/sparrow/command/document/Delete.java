/**
 * 
 */
package com.github.nnest.sparrow.command.document;

import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticDocumentAnalyzer;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.google.common.base.Strings;

/**
 * delete command
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Delete implements ElasticCommand {
	private Class<?> documentType = null;
	private String id = null;
	private String version = null;
	private ElasticDocumentDescriptor documentDescriptor = null;

	public Delete(Class<?> documentType, String id) {
		assert documentType != null : "Document type cannot be null.";
		assert Strings.nullToEmpty(id).trim().length() != 0 : "Id cannot be null.";

		this.documentType = documentType;
		this.id = id;
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
	 * @return the documentType
	 */
	public Class<?> getDocumentType() {
		return documentType;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * with specific version
	 * 
	 * @param version
	 *            version
	 * @return this
	 */
	public Delete withVersion(String version) {
		this.version = version;
		return this;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommand#getCommandKind()
	 */
	@Override
	public ElasticCommandKind getCommandKind() {
		return ElasticCommandKind.DELETE;
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
